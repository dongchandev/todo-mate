import { useState } from "react";
import styled from "styled-components";

export default function TodoInput({ onAdd }: { onAdd: (text: string) => void }) {
    const [text, setText] = useState("");

    const handleSubmit = (e: React.KeyboardEvent<HTMLInputElement>) => {
        if (e.key === "Enter" && !e.nativeEvent.isComposing && text.trim()) {
            onAdd(text.trim());
            setText("");
        }
    };

    return (
        <Input
            type="text"
            value={text}
            onChange={(e) => setText(e.target.value)}
            onKeyDown={handleSubmit}
            placeholder="새 할 일을 입력 후 Enter"
        />
    );
}

const Input = styled.input`
    width: 100%;
    padding: 8px 12px;
    background: #222;
    color: #fff;
    border: none;
    border-radius: 6px;
    outline: none;
`;
