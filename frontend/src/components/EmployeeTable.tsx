import React, { useEffect, useState } from 'react';
import {
  Container,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Typography,
  RadioGroup,
  FormControlLabel,
  Radio,
  Stack,
  Alert,
  Button,
} from '@mui/material';
import { AssignmentData, getAssignmentsData } from '../api';

const EmployeeTable: React.FC = () => {
  const [assignmentData, setAssignmentData] = useState<AssignmentData[]>([]);
  const [view, setView] = useState<'day' | 'week' | 'month'>('day');
  const [dataMode, setDataMode] = useState<'revenue' | 'hours'>('revenue');
  const [error, setError] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  // Helper function to format date
  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', {
      month: 'short',
      day: 'numeric'
    });
  };

  // Helper function to get all unique dates from all assignments
  const getAllUniqueDates = () => {
    const allDates = new Set<string>();
    assignmentData.forEach((consultantData) =>
      consultantData.assignments.forEach((assignment) =>
        assignment.revenueMonth.forEach((month) =>
          month.weeks.forEach((week) =>
            week.days.forEach((day) => allDates.add(day.day))
          )
        )
      )
    );
    return Array.from(allDates).sort();
  };

  // Helper function to get all unique weeks from all assignments
  const getAllUniqueWeeks = () => {
    const allWeeks = new Set<number>();
    assignmentData.forEach((consultantData) =>
      consultantData.assignments.forEach((assignment) =>
        assignment.revenueMonth.forEach((month) =>
          month.weeks.forEach((week) => allWeeks.add(week.weekNumber))
        )
      )
    );
    return Array.from(allWeeks).sort((a, b) => a - b);
  };

  // Helper function to get all unique months from all assignments
  const getAllUniqueMonths = () => {
    const allMonths = new Set<string>();
    assignmentData.forEach((consultantData) =>
      consultantData.assignments.forEach((assignment) =>
        assignment.revenueMonth.forEach((month) => allMonths.add(month.name))
      )
    );
    return Array.from(allMonths).sort();
  };

  const fetchData = async () => {
    setIsLoading(true);
    setError(null);

    try {
      const data = await getAssignmentsData();
      setAssignmentData(data);
    } catch (err) {
      console.error('Error fetching assignments:', err);
      setError(
        err instanceof Error ? err.message : 'An unexpected error occurred'
      );
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  if (error) {
    return (
      <Container>
        <Typography variant="h4" gutterBottom>
          Consultant Booking & Forecast
        </Typography>
        <Alert
          severity="error"
          sx={{ mt: 2 }}
          action={
            <Button
              color="inherit"
              size="small"
              onClick={fetchData}
              disabled={isLoading}
            >
              {isLoading ? 'Retrying...' : 'Retry'}
            </Button>
          }
        >
          {error}
        </Alert>
      </Container>
    );
  }

  return (
    <Container>
      <Typography variant="h4" gutterBottom>
        Consultant Booking & Forecast
      </Typography>

      <Stack direction="row" spacing={4} sx={{ mb: 2 }}>
        {/* Time Period Switcher */}
        <RadioGroup
          row
          value={view}
          onChange={(e) => setView(e.target.value as 'day' | 'week' | 'month')}
        >
          <FormControlLabel value="day" control={<Radio />} label="Day" />
          <FormControlLabel value="week" control={<Radio />} label="Week" />
          <FormControlLabel value="month" control={<Radio />} label="Month" />
        </RadioGroup>

        {/* Data Mode Switcher */}
        <RadioGroup
          row
          value={dataMode}
          onChange={(e) => setDataMode(e.target.value as 'revenue' | 'hours')}
        >
          <FormControlLabel
            value="revenue"
            control={<Radio />}
            label="Revenue"
          />
          <FormControlLabel value="hours" control={<Radio />} label="Hours" />
        </RadioGroup>
      </Stack>

      {/* Table */}
      <TableContainer
        component={Paper}
        sx={{
          mt: 2,
          width: '100%',
        }}
      >
        <Table sx={{ width: '100%' }}>
          <TableHead>
            <TableRow sx={{ backgroundColor: 'grey.200' }}>
              <TableCell
                sx={{
                  width: 100,
                  color: 'text.primary',
                  fontWeight: 'bold',
                  borderRight: 1,
                  borderColor: 'grey.300',
                }}
              >
                Consultant
              </TableCell>
              <TableCell
                sx={{
                  width: 200,
                  color: 'text.primary',
                  fontWeight: 'bold',
                  borderRight: 1,
                  borderColor: 'grey.300',
                }}
              >
                Customer and Project
              </TableCell>

              {/* Total Column - Shows either Revenue or Hours based on mode */}
              <TableCell
                sx={{
                  width: 100,
                  color: 'text.primary',
                  fontWeight: 'bold',
                  borderRight: 1,
                  borderColor: 'grey.300',
                }}
              >
                {dataMode === 'revenue' ? 'Total Revenue' : 'Total Hours'}
              </TableCell>

              {/* Dynamic Headers Based on Selected View */}
              {view === 'day' &&
                getAllUniqueDates().map((date) => (
                  <TableCell
                    key={date}
                    align="center"
                    sx={{
                      color: 'text.primary',
                      fontWeight: 'bold',
                      whiteSpace: 'nowrap',
                      width: 80,
                      minWidth: 80,
                      padding: '16px 8px'
                    }}
                  >
                    {formatDate(date)}
                  </TableCell>
                ))}

              {view === 'week' &&
                getAllUniqueWeeks().map((weekNumber) => (
                  <TableCell
                    key={weekNumber}
                    align="center"
                    sx={{
                      color: 'text.primary',
                      fontWeight: 'bold',
                    }}
                  >
                    Week {weekNumber}
                  </TableCell>
                ))}

              {view === 'month' &&
                getAllUniqueMonths().map((monthName) => (
                  <TableCell
                    key={monthName}
                    align="center"
                    sx={{
                      color: 'text.primary',
                      fontWeight: 'bold',
                    }}
                  >
                    {monthName}
                  </TableCell>
                ))}
            </TableRow>
          </TableHead>

          <TableBody>
            {assignmentData.map((consultantData) =>
              consultantData.assignments.map((assignment) => (
                <TableRow
                  key={`${consultantData.consultant.id}-${assignment.id}`}
                  sx={{
                    '&:nth-of-type(odd)': { backgroundColor: 'action.hover' },
                  }}
                >
                  <TableCell
                    sx={{
                      minWidth: 100,
                      fontWeight: 'bold',
                      borderRight: 1,
                      borderColor: 'grey.200',
                    }}
                  >
                    {consultantData.consultant.name}
                    <Typography
                      variant="caption"
                      display="block"
                      color="text.secondary"
                    >
                      Rate: ${consultantData.consultant.ratePerHour}/h
                    </Typography>
                  </TableCell>
                  <TableCell
                    sx={{
                      minWidth: 200,
                      borderRight: 1,
                      borderColor: 'grey.200',
                    }}
                  >
                    {assignment.customer.name} - {assignment.name}
                  </TableCell>

                  {/* Total Column - Shows either Revenue or Hours based on mode */}
                  <TableCell
                    align="center"
                    sx={{
                      borderRight: 1,
                      borderColor: 'grey.200',
                    }}
                  >
                    {dataMode === 'revenue' 
                      ? `$${assignment.revenue.toFixed()}`
                      : `${assignment.hoursWorked}h`
                    }
                  </TableCell>

                  {/* Dynamic Data Based on Selected View */}
                  {view === 'day' &&
                    getAllUniqueDates().map((date) => {
                      const dayData = assignment.revenueMonth
                        .flatMap((month) => month.weeks)
                        .flatMap((week) => week.days)
                        .find((day) => day.day === date);

                      return (
                        <TableCell key={date} align="center">
                          {dayData
                            ? dataMode === 'revenue'
                              ? `$${dayData.revenue.toFixed()}`
                              : `${dayData.hours || 0}h`
                            : '-'}
                        </TableCell>
                      );
                    })}

                  {view === 'week' &&
                    getAllUniqueWeeks().map((weekNumber) => {
                      const weekData = assignment.revenueMonth
                        .flatMap((month) => month.weeks)
                        .find((week) => week.weekNumber === weekNumber);

                      const weekHours =
                        weekData?.days.reduce(
                          (sum, day) => sum + (day.hours || 0),
                          0
                        ) || 0;

                      return (
                        <TableCell key={weekNumber} align="center">
                          {weekData
                            ? dataMode === 'revenue'
                              ? `$${weekData.revenue.toFixed()}`
                              : `${weekHours}h`
                            : '-'}
                        </TableCell>
                      );
                    })}

                  {view === 'month' &&
                    getAllUniqueMonths().map((monthName) => {
                      const monthData = assignment.revenueMonth.find(
                        (month) => month.name === monthName
                      );

                      const monthHours =
                        monthData?.weeks
                          .flatMap((week) => week.days)
                          .reduce((sum, day) => sum + (day.hours || 0), 0) || 0;

                      return (
                        <TableCell key={monthName} align="center">
                          {monthData
                            ? dataMode === 'revenue'
                              ? `$${monthData.revenue.toFixed()}`
                              : `${monthHours}h`
                            : '-'}
                        </TableCell>
                      );
                    })}
                </TableRow>
              ))
            )}
          </TableBody>
        </Table>
      </TableContainer>
    </Container>
  );
};

export default EmployeeTable;
