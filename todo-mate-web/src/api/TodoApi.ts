import type {ApiResponse} from "./TodoResponse.ts";
import type {Todo} from "../model/Todo.ts";

export default class TodoApi {

    static createTodo = async (content: string, dueDate: string): Promise<Todo> => {
        const response = await fetch("http://localhost:8080/todos", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ content, dueDate }),
        });

        if (!response.ok) {
            throw new Error(`HTTP ${response.status}`);
        }

        const result: ApiResponse<Todo> = await response.json();
        console.log("📬 응답:", result);

        return result.data!;
    };

    static updateTodo = async (id:number, content: string | undefined, memo: string | undefined): Promise<Todo> => {
        const response = await fetch(`http://localhost:8080/todos/${id}`, {
            method: "PATCH",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ content, memo }),
        });

        if (!response.ok) {
            throw new Error(`HTTP ${response.status}`);
        }

        const result: ApiResponse<Todo> = await response.json();
        console.log("📬 응답:", result);

        return result.data!;
    };

    static toggleTodo = async (id:number): Promise<undefined> => {
        const response = await fetch(`http://localhost:8080/todos/${id}/toggle`, {
            method: "PATCH",
            headers: { "Content-Type": "application/json" },
        });

        if (!response.ok) {
            throw new Error(`HTTP ${response.status}`);
        }

        const result = await response.json();
        console.log("📬 응답:", result);
    };

    static deleteTodo = async (id:number): Promise<undefined> => {
        const response = await fetch(`http://localhost:8080/todos/${id}`, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" },
        });

        if (!response.ok) {
            throw new Error(`HTTP ${response.status}`);
        }

        const result = await response.json();
        console.log("📬 응답:", result);
    };
}