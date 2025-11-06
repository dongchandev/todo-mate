export interface Todo {
    id: number;
    text: string;
    date: string;
    memo: string;
    status: "IN_PROGRESS" | "DONE";
}

export interface TodoDateCount {
    date: string;
    count: number;
}

export interface TodoMonthDoneCount {
    count: number;
}