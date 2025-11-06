import dayjs from "dayjs";
import styled from "styled-components";
import { useState } from "react";
import PageContainer from "./components/PageContainer";
import Calendar from "./components/Calendar";
import TodoList from "./components/TodoList";

function App() {
    const [selectedDate, setSelectedDate] = useState(dayjs());
    const [refreshKey, setRefreshKey] = useState(0);
    const [monthStats, setMonthStats] = useState<
        Record<string, { remaining: number }>
    >({});

    const handleSync = () => setRefreshKey((prev) => prev + 1);

    return (
        <AppWrapper>
            <PageContainer>
                <Calendar
                    selectedDate={selectedDate}
                    onSelect={setSelectedDate}
                    refreshKey={refreshKey}
                    monthStats={monthStats}
                    setMonthStats={setMonthStats}
                />
                <TodoList
                    date={selectedDate}
                    onSync={handleSync}
                />
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
