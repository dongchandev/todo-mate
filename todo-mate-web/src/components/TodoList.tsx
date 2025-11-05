import { useState } from "react";
import { Dayjs } from "dayjs";
import styled from "styled-components";
import TodoItem from "./TodoItem";
import TodoInput from "./TodoInput";
import TodoActionModal from "./TodoActionModal";

interface Todo {
    id: number;
    text: string;
    date: string;
    memo?: string;
    status: "IN_PROGRESS" | "DONE";
}

interface Props {
    date: Dayjs;
}

export default function TodoList({ date }: Props) {
    const [todos, setTodos] = useState<Todo[]>([]);
    const [selectedTodo, setSelectedTodo] = useState<Todo | null>(null);

    const filtered = todos.filter(
        (t) => t.date === date.format("YYYY-MM-DD")
    );

    const handleAdd = (text: string) => {
        if (!text.trim()) return;

        const newTodo: Todo = {
            id: Date.now(),
            text,
            date: date.format("YYYY-MM-DD"),
            memo: "",
            status: "IN_PROGRESS",
        };
        setTodos([newTodo, ...todos]);
    };

    const handleToggle = (id: number) => {
        setTodos((prev) =>
            prev.map((t) =>
                t.id === id
                    ? {
                        ...t,
                        status:
                            t.status === "IN_PROGRESS" ? "DONE" : "IN_PROGRESS",
                    }
                    : t
            )
        );
    };

    const handleDelete = (id: number) => {
        setTodos((prev) => prev.filter((t) => t.id !== id));
        setSelectedTodo(null);
    };

    const handleEdit = (id: number, newText: string) => {
        setTodos((prev) =>
            prev.map((t) => (t.id === id ? { ...t, text: newText } : t))
        );
        setSelectedTodo(null);
    };

    const handleEditMemo = (id: number, newMemo: string) => {
        setTodos((prev) =>
            prev.map((t) => (t.id === id ? { ...t, memo: newMemo } : t))
        );
    };

    return (
        <Wrapper>
            {filtered.length === 0 ? (
                <Empty>할 일이 없습니다</Empty>
            ) : (
                filtered.map((t) => (
                    <TodoItem
                        key={t.id}
                        todo={t}
                        onToggle={handleToggle}
                        onOpenAction={(todo) => setSelectedTodo(todo)}
                    />
                ))
            )}

            <Divider />
            <TodoInput onAdd={handleAdd} />

            {selectedTodo && (
                <TodoActionModal
                    todo={selectedTodo}
                    onClose={() => setSelectedTodo(null)}
                    onDelete={handleDelete}
                    onEdit={handleEdit}
                    onEditMemo={handleEditMemo}
                />
            )}
        </Wrapper>
    );
}

const Wrapper = styled.div`
    flex: 0 0 50%;
`;

const Empty = styled.p`
    color: #aaa;
`;

const Divider = styled.div`
    margin: 12px 0;
    border-bottom: 1px solid #333;
`;
