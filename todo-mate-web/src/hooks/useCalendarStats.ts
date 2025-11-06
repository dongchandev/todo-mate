import { useState, useEffect } from "react";
import { Dayjs } from "dayjs";
import TodoApi from "../api/TodoApi";

export function useCalendarStats(
    currentMonth: Dayjs,
    refreshKey: number,
    setMonthStats: React.Dispatch<
        React.SetStateAction<Record<string, { remaining: number }>>
    >,
    setMonthSummary?: React.Dispatch<React.SetStateAction<{ done: number }>>
) {
    const [monthSummary, _setMonthSummary] = useState<{ done: number }>({ done: 0 });
    const [loading, setLoading] = useState(false);

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
                _setMonthSummary({ done: doneCount.data?.count ?? 0 });
                setMonthSummary?.({ done: doneCount.data?.count ?? 0 });
            } catch (e) {
                console.error("달력 통계 불러오기 실패", e);
            } finally {
                setLoading(false);
            }
        };

        fetchStats();
    }, [currentMonth, refreshKey]);

    return { monthSummary, loading, setMonthSummary: _setMonthSummary };
}
