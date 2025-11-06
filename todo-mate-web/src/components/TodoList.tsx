import { Dayjs } from "dayjs";
import styled from "styled-components";
import TodoItem from "./TodoItem";
import TodoInput from "./TodoInput";
import TodoActionModal from "./TodoActionModal";
import { useTodos } from "../hooks/useTodos";

interface Props {
    date: Dayjs;
    onSync: () => void;
}

export default function TodoList({ date, onSync }: Props) {
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

    const handleAddAndSync = async (text: string) => {
        await handleAdd(text);
        onSync();
    };

    const handleToggleAndSync = async (id: number) => {
        await handleToggle(id);
        onSync();
    };

    const handleDeleteAndSync = async (id: number) => {
        await handleDelete(id);
        onSync();
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
                        onToggle={() => handleToggleAndSync(t.id)}
                        onOpenAction={setSelectedTodo}
                    />
                ))
            )}

            <Divider />
            <TodoInput onAdd={handleAddAndSync} />

            {selectedTodo && (
                <TodoActionModal
                    todo={selectedTodo}
                    onClose={() => setSelectedTodo(null)}
                    onDelete={(id) => handleDeleteAndSync(id)}
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
