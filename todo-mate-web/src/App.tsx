import dayjs from "dayjs";
import PageContainer from "./components/PageContainer.tsx";
import TodoList from "./components/TodoList.tsx";
import Calendar from "./components/Calendar.tsx";
import styled from "styled-components";

function App() {
    const selectedDate = dayjs("2025-11-04");

    return (
        <AppWrapper>
            <PageContainer>
                <Calendar />
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
