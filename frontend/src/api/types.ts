export type Customer = {
  id: number;
  name: string;
};
export type Consultant = {
  id: number;
  name: string;
  ratePerHour: number;
};

type DayData = {
  day: string;
  hours: number;
  revenue: number;
};

type WeekData = {
  weekNumber: number;
  revenue: number;
  days: DayData[];
};

type RevenueMonth = {
  month: string;
  weeks: WeekData[];
};

export type Assignment = {
  id: number;
  name: string;
  revenue: number;
  hoursWorked: number;
  customer: Customer;
  revenueMonth: RevenueMonth[];
}

export type AssignmentData = {
  consultant: Consultant;
  assignments: Assignment[];
};
