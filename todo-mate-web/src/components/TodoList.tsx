import {Dayjs} from "dayjs";
import styled from "styled-components";
import TodoItem from "./TodoItem";
import TodoInput from "./TodoInput";
import TodoActionModal from "./TodoActionModal";
import {useTodos} from "../hooks/useTodos.ts";

interface Props {
    date: Dayjs;
}

export default function TodoList({ date }: Props) {
    const {
        filtered,
        selectedTodo,
        setSelectedTodo,
        handleAdd,
        handleToggle,
        handleDelete,
        handleEdit,
        handleEditMemo,
    } = useTodos(date);

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
