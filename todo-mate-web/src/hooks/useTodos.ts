import {useState, useCallback, useEffect} from "react";
import TodoApi from "../api/TodoApi";
import type { Todo } from "../models/Todo";
import { Dayjs } from "dayjs";

export function useTodos(date: Dayjs) {
    const [todos, setTodos] = useState<Todo[]>([]);
    const [selectedTodo, setSelectedTodo] = useState<Todo | null>(null);

    const filtered = todos.filter((t) => t.date === date.format("YYYY-MM-DD"));

    useEffect(() => {
        const fetchTodos = async () => {
            try {
                const response = await TodoApi.getTodosByDate(date.format("YYYY-MM-DD"));
                setTodos(response.data ?? []);
            } catch (e) {
                console.error("할 일 불러오기 실패:", e);
            }
        };
        fetchTodos();
    }, [date]);

    const handleAdd = useCallback(async (text: string) => {
        if (!text.trim()) return;
        const newTodo = await TodoApi.createTodo(text, date.format("YYYY-MM-DD"));
        setTodos((prev) => [newTodo, ...prev]);
    }, [date]);

    const handleToggle = useCallback(async (id: number) => {
        await TodoApi.toggleTodo(id);
        setTodos((prev) =>
            prev.map((t) =>
                t.id === id
                    ? { ...t, status: t.status === "IN_PROGRESS" ? "DONE" : "IN_PROGRESS" }
                    : t
            )
        );
    }, []);

    const handleDelete = useCallback(async (id: number) => {
        await TodoApi.deleteTodo(id);
        setTodos((prev) => prev.filter((t) => t.id !== id));
        setSelectedTodo(null);
    }, []);

    const handleEdit = useCallback(async (id: number, newText: string) => {
        await TodoApi.updateTodo(id, newText, undefined);
        setTodos((prev) =>
            prev.map((t) => (t.id === id ? { ...t, text: newText } : t))
        );
        setSelectedTodo(null);
    }, []);

    const handleEditMemo = useCallback(async (id: number, newMemo: string) => {
        await TodoApi.updateTodo(id, undefined, newMemo);
        setTodos((prev) =>
            prev.map((t) => (t.id === id ? { ...t, memo: newMemo } : t))
        );
    }, []);

    return {
        todos,
        filtered,
        selectedTodo,
        setSelectedTodo,
        handleAdd,
        handleToggle,
        handleDelete,
        handleEdit,
        handleEditMemo,
    };
}
