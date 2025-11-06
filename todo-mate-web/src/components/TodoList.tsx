import { useState } from "react";
import { Dayjs } from "dayjs";
import styled from "styled-components";
import TodoItem from "./TodoItem";
import TodoInput from "./TodoInput";
import TodoActionModal from "./TodoActionModal";
import TodoApi from "../api/TodoApi.ts";
import type {Todo} from "../model/Todo.ts";

interface Props {
    date: Dayjs;
}

export default function TodoList({ date }: Props) {
    const [todos, setTodos] = useState<Todo[]>([]);
    const [selectedTodo, setSelectedTodo] = useState<Todo | null>(null);

    const filtered = todos.filter(
        (t) => t.date === date.format("YYYY-MM-DD")
    );

    const handleAdd = async (text: string) => {

        try {
            if (!text.trim()) return;
            const newTodo = await TodoApi.createTodo(text, date.format("YYYY-MM-DD"));
            setTodos((prev) => [newTodo, ...prev]);
        } catch (e) {
            console.error(e);
            alert("할 일 추가 중 오류 발생!");
        }
    };

    const handleToggle = async (id: number) => {
        await TodoApi.toggleTodo(id)
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

    const handleDelete = async (id: number) => {
        await TodoApi.deleteTodo(id);
        setTodos((prev) => prev.filter((t) => t.id !== id));
        setSelectedTodo(null);
    };

    const handleEdit = async (id: number, newText: string) => {
        await TodoApi.updateTodo(id, newText, undefined);
        setTodos((prev) =>
            prev.map((t) => (t.id === id ? { ...t, text: newText } : t))
        );
        setSelectedTodo(null);
    };

    const handleEditMemo = async (id: number, newMemo: string) => {
        await TodoApi.updateTodo(id, undefined, newMemo);
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
