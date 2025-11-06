import styled from "styled-components";
import dayjs, { Dayjs } from "dayjs";
import { ChevronLeft, ChevronRight } from "lucide-react";
import { useState } from "react";
import { useCalendarStats } from "../hooks/useCalendarStats";
import {getCalendarDays} from "../utils/calendar.ts";
import { colors } from "../styles/colors.ts";

interface Props {
    selectedDate: Dayjs;
    onSelect: (date: Dayjs) => void;
    refreshKey: number;
    monthStats: Record<string, { remaining: number }>;
    setMonthStats: React.Dispatch<
        React.SetStateAction<Record<string, { remaining: number }>>
    >;
}

export default function Calendar({
                                     selectedDate,
                                     onSelect,
                                     refreshKey,
                                     monthStats,
                                     setMonthStats,
                                 }: Props) {
    const [currentMonth, setCurrentMonth] = useState(dayjs());
    const [monthSummary, setMonthSummary] = useState<{ done: number }>({ done: 0 });
    const { loading } = useCalendarStats(currentMonth, refreshKey, setMonthStats, setMonthSummary);
    const days = getCalendarDays(currentMonth);

    return (
        <CalendarWrapper>
            <Header>
                <NavButton onClick={() => setCurrentMonth(currentMonth.subtract(1, "month"))}>
                    <ChevronLeft size={18} />
                </NavButton>
                <MonthLabel>{currentMonth.format("YYYY년 MM월")}</MonthLabel>
                <NavButton onClick={() => setCurrentMonth(currentMonth.add(1, "month"))}>
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
                    const isSelected = d.isSame(selectedDate, "day");
                    const isCurrentMonth = d.isSame(currentMonth, "month");

                    return (
                        <DayCell
                            key={key}
                            $dim={!isCurrentMonth}
                            $selected={isSelected}
                            onClick={() => onSelect(d)}
                        >
                            <DateNumber>{d.date()}</DateNumber>
                            <RemainingCount $zero={remaining === 0}>
                                {loading ? (
                                    "로딩 중"
                                ) : remaining > 0 ? (
                                    `${remaining}개 남음`
                                ) : (
                                    "완료"
                                )}
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
    background-color: ${colors.background};
    border-radius: 16px;
    margin-right: 20px;
    padding: 16px;
    box-sizing: border-box;
    color: ${colors.textPrimary};
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
    color: ${colors.textPrimary}
    cursor: pointer;
    padding: 6px;
    border-radius: 8px;
    &:hover {
        background: ${colors.surface};
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
    color: ${colors.textPrimary};
    font-size: 14px;
`;

const Highlight = styled.span`
    color: ${colors.accent};
    font-weight: 700;
`;

const DayHeaderRow = styled.div`
    display: grid;
    grid-template-columns: repeat(7, 1fr);
    text-align: center;
    font-weight: 600;
    margin-bottom: 8px;
    color: ${colors.textSecondary};
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
    color: ${({ $dim }) => ($dim ? colors.textMuted : colors.textPrimary)};
    background: ${({ $selected }) => ($selected ? colors.border : "transparent")};
    &:hover {
        background: ${colors.surface};
    }
`;

const DateNumber = styled.div`
    font-size: 16px;
`;

const RemainingCount = styled.div<{ $zero?: boolean }>`
    font-size: 12px;
    color: ${({ $zero }) => ($zero ? colors.accent : colors.textSecondary)};
    margin-top: 4px;
`;
