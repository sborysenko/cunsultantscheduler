import { API_BASE_URL } from './const';
import { AssignmentData } from './types';

export async function getAssignmentsData(): Promise<AssignmentData[]> {
  return fetch(`${API_BASE_URL}/assignments`)
    .then((response) => response.json())
    .catch((error) => console.error('Error fetching assignments: ', error));
}

export async function getAssignmentsDataMock(): Promise<AssignmentData[]> {
  return Promise.resolve([
    {
      consultant: {
        id: 1,
        name: 'Alice Johnson',
        ratePerHour: 50,
        hoursPerDay: 8,
      },
      assignments: [
        {
          id: 1,
          name: 'Assignment 1',
          revenue: 1600,
          hoursWorked: 32,
          customer: {
            id: 1,
            name: 'Company A',
          },
          revenueMonth: [
            {
              month: 'June',
              weeks: [
                {
                  weekNumber: 11,
                  revenue: 1600,
                  days: [
                    {
                      day: '06.06.2025',
                      hours: 8,
                      revenue: 400,
                    },
                    {
                      day: '07.06.2025',
                      hours: 8,
                      revenue: 400,
                    },
                    {
                      day: '08.06.2025',
                      hours: 8,
                      revenue: 400,
                    },
                    {
                      day: '09.06.2025',
                      hours: 8,
                      revenue: 400,
                    },
                  ],
                },
                {
                  weekNumber: 12,
                  revenue: 2000,
                  days: [
                    {
                      day: '10.06.2025',
                      hours: 8,
                      revenue: 400,
                    },
                    {
                      day: '11.06.2025',
                      hours: 8,
                      revenue: 400,
                    },
                    {
                      day: '12.06.2025',
                      hours: 8,
                      revenue: 400,
                    },
                    {
                      day: '13.06.2025',
                      hours: 8,
                      revenue: 400,
                    },
                    {
                      day: '14.06.2025',
                      hours: 8,
                      revenue: 400,
                    },
                  ],
                },
              ],
            },
          ],
        },
      ],
    },
    {
      consultant: {
        id: 2,
        name: 'Bob Smith',
        ratePerHour: 60,
        hoursPerDay: 6,
      },
      assignments: [
        {
          id: 2,
          name: 'Assignment 2',
          revenue: 1800,
          hoursWorked: 30,
          customer: {
            id: 2,
            name: 'Company B',
          },
          revenueMonth: [
            {
              month: 'June',
              weeks: [
                {
                  weekNumber: 11,
                  revenue: 1200,
                  days: [
                    {
                      day: '06.06.2025',
                      hours: 6,
                      revenue: 360,
                    },
                    {
                      day: '07.06.2025',
                      hours: 6,
                      revenue: 360,
                    },
                    {
                      day: '08.06.2025',
                      hours: 6,
                      revenue: 360,
                    },
                  ],
                },
                {
                  weekNumber: 12,
                  revenue: 600,
                  days: [
                    {
                      day: '09.06.2025',
                      hours: 6,
                      revenue: 360,
                    },
                    {
                      day: '10.06.2025',
                      hours: 6,
                      revenue: 360,
                    },
                  ],
                },
              ],
            },
          ],
        },
      ],
    },
  ]);
}
