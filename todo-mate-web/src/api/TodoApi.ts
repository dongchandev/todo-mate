import type {ApiResponse} from "./TodoResponse.ts";
import type {Todo, TodoDateCount, TodoMonthDoneCount} from "../model/Todo.ts";

export default class TodoApi {

    static createTodo = async (content: string, dueDate: string): Promise<Todo> => {
        const response = await fetch("http://localhost:8080/todos", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ content, dueDate }),
        });
        const result: ApiResponse<Todo> = await response.json();


        if (result.status == 201) {
            return result.data!;
        } else if (result.status == 400) {
            alert(result.message);
            throw new Error
        } else {
            alert("Todo 생성 중 오류가 발생했습니다.");
            throw new Error
        }
    };

    static updateTodo = async (id:number, content: string | undefined, memo: string | undefined): Promise<Todo> => {
        const response = await fetch(`http://localhost:8080/todos/${id}`, {
            method: "PATCH",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ content, memo }),
        });

        const result: ApiResponse<Todo> = await response.json();

        if (result.status == 201) {
            return result.data!;
        } else if (result.status == 400) {
            alert(result.message);
            throw new Error
        } else {
            alert("Todo 수정 중 오류가 발생했습니다.");
            throw new Error
        }

        return result.data!;
    };

    static toggleTodo = async (id:number): Promise<undefined> => {
        const response = await fetch(`http://localhost:8080/todos/${id}/toggle`, {
            method: "PATCH",
            headers: { "Content-Type": "application/json" },
        });

        const result = await response.json();
        console.log("📬 응답:", result);
    };

    static deleteTodo = async (id:number): Promise<undefined> => {
        const response = await fetch(`http://localhost:8080/todos/${id}`, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" },
        });

        const result = await response.json();
        console.log("📬 응답:", result);
    };

    static async getMonthRemainingCount(year: number, month: number): Promise<ApiResponse<TodoDateCount[]>> {
        const res = await fetch(`http://localhost:8080/todos/remaining?year=${year}&month=${month}`, {
            method: "GET",
            headers: { "Content-Type": "application/json" },
        });
        console.log("📬 응답:", res);
        return res.json();
    }


    static async getMonthDoneCount(year: number, month: number): Promise<ApiResponse<TodoMonthDoneCount>> {
        const res = await fetch(`http://localhost:8080/todos/done-count?year=${year}&month=${month}`, {
            method: "GET",
            headers: { "Content-Type": "application/json" },
        });
        console.log("📬 응답:", res);
        return res.json();
    }

    static async getTodosByDate(date: string) {
        const res = await fetch(`http://localhost:8080/todos?date=${date}`);
        console.log("📬 응답:", res);
        return res.json();
    }
}