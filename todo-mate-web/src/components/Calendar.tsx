import styled from "styled-components";
import dayjs, { Dayjs } from "dayjs";
import { useState, useEffect } from "react";
import { ChevronLeft, ChevronRight } from "lucide-react";
import TodoApi from "../api/TodoApi";

interface Props {
    selectedDate: Dayjs;
    onSelect: (date: Dayjs) => void;
}

export default function Calendar({ selectedDate, onSelect }: Props) {
    const [currentMonth, setCurrentMonth] = useState(dayjs());
    const [monthStats, setMonthStats] = useState<Record<string, { remaining: number }>>({});
    const [monthSummary, setMonthSummary] = useState<{ done: number }>({ done: 0 });
    const [loading, setLoading] = useState(false);

    const startOfMonth = currentMonth.startOf("month");
    const endOfMonth = currentMonth.endOf("month");
    const startDate = startOfMonth.startOf("week");
    const endDate = endOfMonth.endOf("week");

    const days: Dayjs[] = [];
    let d = startDate;
    while (d.isBefore(endDate) || d.isSame(endDate, "day")) {
        days.push(d);
        d = d.add(1, "day");
    }

    useEffect(() => {
        const fetchStats = async () => {
            setLoading(true);
            try {
                const year = currentMonth.year();
                const month = currentMonth.month() + 1;

                const remainingData = await TodoApi.getMonthRemainingCount(year, month);
                const doneCount = await TodoApi.getMonthDoneCount(year, month);

                const formatted = (remainingData.data ?? []).reduce(
                    (acc, cur) => {
                        acc[cur.date] = { remaining: cur.count };
                        return acc;
                    },
                    {} as Record<string, { remaining: number }>
                );

                setMonthStats(formatted);
                setMonthSummary({ done: doneCount.data?.count! });
            } catch (e) {
                console.error("달력 통계 불러오기 실패", e);
            } finally {
                setLoading(false);
            }
        };

        fetchStats();
    }, [currentMonth]);

    const handlePrevMonth = () => setCurrentMonth(currentMonth.subtract(1, "month"));
    const handleNextMonth = () => setCurrentMonth(currentMonth.add(1, "month"));
    const isSameDay = (a: Dayjs, b: Dayjs) => a.isSame(b, "day");

    return (
        <CalendarWrapper>
            <Header>
                <NavButton onClick={handlePrevMonth}>
                    <ChevronLeft size={18} />
                </NavButton>
                <MonthLabel>{currentMonth.format("YYYY년 MM월")}</MonthLabel>
                <NavButton onClick={handleNextMonth}>
                    <ChevronRight size={18} />
                </NavButton>
            </Header>

            <MonthStats>
                {loading ? (
                    <span>로딩 중...</span>
                ) : (
                    <span>
            이번 달 완료: <Highlight>{monthSummary.done}</Highlight>개
          </span>
                )}
            </MonthStats>

            <DayHeaderRow>
                {["일", "월", "화", "수", "목", "금", "토"].map((d) => (
                    <DayHeader key={d}>{d}</DayHeader>
                ))}
            </DayHeaderRow>

            <Grid>
                {days.map((d) => {
                    const key = d.format("YYYY-MM-DD");
                    const remaining = monthStats[key]?.remaining ?? 0;
                    const isCurrentMonth = d.isSame(currentMonth, "month");
                    const isToday = d.isSame(dayjs(), "day");
                    const isSelected = isSameDay(d, selectedDate);

                    return (
                        <DayCell
                            key={key}
                            $dim={!isCurrentMonth}
                            $today={isToday}
                            $selected={isSelected}
                            onClick={() => onSelect(d)}
                        >
                            <DateNumber>{d.date()}</DateNumber>
                            <RemainingCount $zero={remaining === 0}>
                                {remaining > 0 ? `${remaining}개 남음` : "완료"}
                            </RemainingCount>
                        </DayCell>
                    );
                })}
            </Grid>
        </CalendarWrapper>
    );
}

const CalendarWrapper = styled.div`
    flex: 0 0 45%;
    max-width: 45%;
    aspect-ratio: 1 / 1;
    overflow: hidden;
    background-color: #1e1e1e;
    border-radius: 16px;
    margin-right: 20px;
    padding: 16px;
    box-sizing: border-box;
    color: #fff;
    display: flex;
    flex-direction: column;
`;


const Header = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
`;

const NavButton = styled.button`
    background: none;
    border: none;
    color: #fff;
    cursor: pointer;
    padding: 6px;
    border-radius: 8px;
    &:hover {
        background: #2b2b2b;
    }
`;

const MonthLabel = styled.div`
    font-size: 18px;
    font-weight: 600;
`;

const MonthStats = styled.div`
    margin-top: 10px;
    margin-bottom: 16px;
    text-align: center;
    color: #ccc;
    font-size: 14px;
`;

const Highlight = styled.span`
    color: #4caf50;
    font-weight: 700;
`;

const DayHeaderRow = styled.div`
    display: grid;
    grid-template-columns: repeat(7, 1fr);
    text-align: center;
    font-weight: 600;
    margin-bottom: 8px;
    color: #bbb;
`;

const DayHeader = styled.div`
    padding: 4px;
`;

const Grid = styled.div`
    display: grid;
    grid-template-columns: repeat(7, 1fr);
    grid-auto-rows: minmax(40px, 1fr);
    gap: 4px;
    flex-grow: 0;
`;

const DayCell = styled.div<{ $dim?: boolean; $today?: boolean; $selected?: boolean }>`
    aspect-ratio: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    border-radius: 10px;
    cursor: pointer;
    font-weight: ${({ $selected }) => ($selected ? 700 : 400)};
    color: ${({ $dim }) => ($dim ? "#555" : "#fff")};
    background: ${({ $selected }) => ($selected ? "#333" : "transparent")};
    &:hover {
        background: #2b2b2b;
    }
`;

const DateNumber = styled.div`
    font-size: 16px;
`;

const RemainingCount = styled.div<{ $zero?: boolean }>`
    font-size: 12px;
    color: ${({ $zero }) => ($zero ? "#4caf50" : "#aaa")};
    margin-top: 4px;
`;
