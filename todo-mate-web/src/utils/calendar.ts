import type {Dayjs} from "dayjs";

export function getCalendarDays(currentMonth: Dayjs): Dayjs[] {
    const days: Dayjs[] = [];
    const start = currentMonth.startOf("month").startOf("week");
    const end = currentMonth.endOf("month").endOf("week");

    let d = start;
    while (d.isBefore(end) || d.isSame(end, "day")) {
        days.push(d);
        d = d.add(1, "day");
    }

    return days;
}