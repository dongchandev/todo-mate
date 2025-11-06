export interface Todo {
    id: number;
    text: string;
    date: string;
    memo: string;
    status: "IN_PROGRESS" | "DONE";
}