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
} from '@mui/material';
import { AssignmentData, getAssignmentsData } from '../api';

const EmployeeTable: React.FC = () => {
  const [assignments, setAssignments] = useState<AssignmentData[]>([]);

  useEffect(() => {
    // Fetch employee data from REST endpoint
    getAssignmentsData().then((data) => {
      setAssignments(data);
    });
  }, []);

  return (
    <Container>
      <Typography variant="h4" gutterBottom>
        Consultant Booking & Forecast
      </Typography>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Consultant</TableCell>
              <TableCell>Customer and Project</TableCell>
              {assignments[0]?.assignments?.[0]?.revenueMonth?.flatMap(
                (month) =>
                  month.weeks.map((week) => (
                    <TableCell key={`week-${week.weekNumber}`} align="center">
                      Week {week.weekNumber}
                    </TableCell>
                  ))
              )}
            </TableRow>
          </TableHead>
          <TableBody>
            {assignments.map((assignmentData) => (
              <React.Fragment key={assignmentData.consultant.id}>
                {assignmentData.assignments.map((assignment) => (
                  <TableRow key={assignment.id}>
                    <TableCell>{assignmentData.consultant.name}</TableCell>
                    <TableCell>
                      {assignment.customer.name} - {assignment.name}
                    </TableCell>
                    {assignment.revenueMonth.flatMap((month) =>
                      month.weeks.map((week) => (
                        <TableCell
                          key={`revenue-${week.weekNumber}`}
                          align="center"
                        >
                          {week.revenue.toFixed(1)}
                        </TableCell>
                      ))
                    )}
                  </TableRow>
                ))}
                <TableRow>
                  <TableCell colSpan={2} style={{ fontWeight: 'bold' }}>
                    Free Capacity
                  </TableCell>
                  {assignmentData.assignments[0]?.revenueMonth.flatMap(
                    (month) =>
                      month.weeks.map((week) => (
                        <TableCell
                          key={`capacity-${week.weekNumber}`}
                          align="center"
                          style={{ fontWeight: 'bold' }}
                        >
                          {(
                            40 -
                            week.revenue / assignmentData.consultant.ratePerHour
                          ).toFixed(1)}
                        </TableCell>
                      ))
                  )}
                </TableRow>
              </React.Fragment>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Container>
  );
};

export default EmployeeTable;
