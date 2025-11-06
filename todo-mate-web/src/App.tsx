import dayjs from "dayjs";
import PageContainer from "./components/PageContainer.tsx";
import TodoList from "./components/TodoList.tsx";
import Calendar from "./components/Calendar.tsx";
import styled from "styled-components";
import TodoApi from "./api/TodoApi.ts";
import {useEffect, useState} from "react";
import type {Todo} from "./model/Todo.ts";

function App() {
    const [selectedDate, setSelectedDate] = useState(dayjs());
    const [todos, setTodos] = useState<Todo[]>([]);

    useEffect(() => {
        (async () => {
            const data = await TodoApi();
            setTodos(data);
        })();
    }, []);

    return (
        <AppWrapper>
            <PageContainer>
                <Calendar
                    todos={todos}
                    selectedDate={selectedDate}
                    onSelect={setSelectedDate}
                />
                <TodoList date={selectedDate} />
            </PageContainer>
        </AppWrapper>
    );
}

export default App;

const AppWrapper = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
`;
