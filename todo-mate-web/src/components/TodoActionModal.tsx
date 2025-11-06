import { createPortal } from "react-dom";
import styled from "styled-components";
import { Pencil, Trash2, FileText, Check } from "lucide-react";
import { useState } from "react";

export default function TodoActionModal({
                                            todo,
                                            onClose,
                                            onDelete,
                                            onEdit,
                                            onEditMemo,
                                        }: {
    todo: { id: number; text: string; memo?: string };
    onClose: () => void;
    onDelete: (id: number) => void;
    onEdit: (id: number, newText: string) => void;
    onEditMemo: (id: number, newMemo: string) => void;
}) {
    const [isEditing, setIsEditing] = useState(false);
    const [editText, setEditText] = useState(todo.text);
    const [memo, setMemo] = useState(todo.memo || "");

    const modalRoot = document.getElementById("modal-root") || document.body;

    const modal = (
        <Overlay onClick={onClose}>
            <Modal onClick={(e) => e.stopPropagation()}>
                <Header>
                    {isEditing ? (
                        <EditInput
                            value={editText}
                            onChange={(e) => setEditText(e.target.value)}
                            placeholder="새 할 일을 입력"
                            autoFocus
                        />
                    ) : (
                        todo.text
                    )}
                </Header>

                <ButtonRow>
                    {isEditing ? (
                        <ActionButton
                            onClick={() => {
                                onEdit(todo.id, editText);
                                setIsEditing(false);
                            }}
                        >
                            <Check size={18} />
                            저장
                        </ActionButton>
                    ) : (
                        <ActionButton onClick={() => setIsEditing(true)}>
                            <Pencil size={18} />
                            수정
                        </ActionButton>
                    )}
                    <ActionButton $delete onClick={() => onDelete(todo.id)}>
                        <Trash2 size={18} />
                        삭제
                    </ActionButton>
                </ButtonRow>

                <MemoBox>
                    <FileText size={18} />
                    <span>메모</span>
                </MemoBox>

                <MemoArea
                    value={memo}
                    onChange={(e) => setMemo(e.target.value)}
                    onBlur={() => {
                        if (memo !== todo.memo) onEditMemo(todo.id, memo);
                    }}
                    placeholder="메모를 입력하세요..."
                />
            </Modal>
        </Overlay>
    );

    return createPortal(modal, modalRoot);
}

const Overlay = styled.div`
    position: fixed;
    inset: 0;
    background: rgba(0, 0, 0, 0.6);
    display: flex;
    justify-content: center;
    align-items: flex-end;
    z-index: 1000;
`;

const Modal = styled.div`
    background: #1e1e1e;
    width: 100%;
    max-width: 500px;
    border-radius: 16px 16px 0 0;
    padding: 20px;
    color: #fff;
    box-sizing: border-box;
`;

const Header = styled.div`
    text-align: center;
    margin-bottom: 16px;
    font-weight: 700;
`;

const EditInput = styled.input`
    width: 100%;
    background: #111;
    border: 1px solid #333;
    border-radius: 6px;
    padding: 8px;
    color: #fff;
    outline: none;
`;

const ButtonRow = styled.div`
    display: flex;
    gap: 12px;
    justify-content: space-between;
`;

const ActionButton = styled.button<{ $delete?: boolean }>`
    flex: 1;
    background: ${({ $delete }) => ($delete ? "#3a2b2b" : "#2b2b2b")};
    color: ${({ $delete }) => ($delete ? "#ff6b6b" : "#fff")};
    border: none;
    border-radius: 10px;
    padding: 12px;
    font-weight: 600;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 6px;
    cursor: pointer;
`;

const MemoBox = styled.div`
    margin-top: 20px;
    display: flex;
    align-items: center;
    gap: 8px;
    font-weight: 600;
    color: #ffca28;
`;

const MemoArea = styled.textarea`
    margin-top: 10px;
    width: 100%;
    height: 200px;
    background: #111;
    border-radius: 10px;
    padding: 10px;
    color: #ddd;
    border: none;
    resize: none;
    outline: none;
    font-family: inherit;
    font-size: 14px;
    box-sizing: border-box;
`;
