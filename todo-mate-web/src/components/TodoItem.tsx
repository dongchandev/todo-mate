import styled from "styled-components";
import { MoreHorizontal } from "lucide-react";
import type {Todo} from "../model/Todo.ts";


interface Props {
    todo: Todo;
    onToggle: (id: number) => void;
    onOpenAction: (todo: Todo) => void;
}

export default function TodoItem({ todo, onToggle, onOpenAction }: Props) {
    const isDone = todo.status === "DONE";

    return (
        <Item done={isDone} onClick={() => onToggle(todo.id)}>
            <Checkbox type="checkbox" checked={isDone} readOnly />
            <span>{todo.text}</span>

            <MoreHorizontal
                size={18}
                onClick={(e) => {
                    e.stopPropagation();
                    onOpenAction(todo);
                }}
            />
        </Item>
    );
}

const Item = styled.div<{ done: boolean }>`
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 10px;
    padding: 8px 12px;
    border-radius: 6px;
    color: #fff;
    text-decoration: ${({ done }) => (done ? "line-through" : "none")};
    cursor: pointer;

    span {
        flex: 1;
        text-align: left;
    }
`;

const Checkbox = styled.input`
    width: 18px;
    height: 18px;
    accent-color: #646cff;
    pointer-events: none;
`;
