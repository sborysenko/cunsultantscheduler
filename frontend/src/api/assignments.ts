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
        ratePerHour: 50.0,
        hoursPerDay: null,
      },
      assignments: [
        {
          id: 1,
          name: 'Assignment 1',
          revenue: 1200.0,
          hoursWorked: 24,
          customer: {
            id: 1,
            name: 'Company A',
          },
          revenueMonth: [
            {
              name: 'MARCH',
              revenue: 1200.0,
              weeks: [
                {
                  weekNumber: 1,
                  revenue: 400.0,
                  days: [
                    {
                      day: '2025-03-01',
                      revenue: 400.0,
                    },
                  ],
                },
                {
                  weekNumber: 2,
                  revenue: 800.0,
                  days: [
                    {
                      day: '2025-03-02',
                      revenue: 400.0,
                    },
                    {
                      day: '2025-03-03',
                      revenue: 400.0,
                    },
                  ],
                },
              ],
            },
          ],
        },
        {
          id: 2,
          name: 'Assignment 2',
          revenue: 1050.0,
          hoursWorked: 21,
          customer: {
            id: 2,
            name: 'Company B',
          },
          revenueMonth: [
            {
              name: 'MARCH',
              revenue: 1050.0,
              weeks: [
                {
                  weekNumber: 2,
                  revenue: 1050.0,
                  days: [
                    {
                      day: '2025-03-05',
                      revenue: 400.0,
                    },
                    {
                      day: '2025-03-06',
                      revenue: 400.0,
                    },
                    {
                      day: '2025-03-07',
                      revenue: 250.0,
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
        ratePerHour: 60.0,
        hoursPerDay: null,
      },
      assignments: [
        {
          id: 3,
          name: 'Assignment 3',
          revenue: 1440.0,
          hoursWorked: 24,
          customer: {
            id: 1,
            name: 'Company A',
          },
          revenueMonth: [
            {
              name: 'MARCH',
              revenue: 1440.0,
              weeks: [
                {
                  weekNumber: 1,
                  revenue: 480.0,
                  days: [
                    {
                      day: '2025-03-01',
                      revenue: 480.0,
                    },
                  ],
                },
                {
                  weekNumber: 2,
                  revenue: 960.0,
                  days: [
                    {
                      day: '2025-03-02',
                      revenue: 480.0,
                    },
                    {
                      day: '2025-03-03',
                      revenue: 480.0,
                    },
                  ],
                },
              ],
            },
          ],
        },
        {
          id: 4,
          name: 'Assignment 4',
          revenue: 1260.0,
          hoursWorked: 21,
          customer: {
            id: 2,
            name: 'Company B',
          },
          revenueMonth: [
            {
              name: 'MARCH',
              revenue: 1260.0,
              weeks: [
                {
                  weekNumber: 2,
                  revenue: 1260.0,
                  days: [
                    {
                      day: '2025-03-05',
                      revenue: 480.0,
                    },
                    {
                      day: '2025-03-06',
                      revenue: 480.0,
                    },
                    {
                      day: '2025-03-07',
                      revenue: 300.0,
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
        id: 3,
        name: 'Scott Frederick',
        ratePerHour: 40.0,
        hoursPerDay: null,
      },
      assignments: [
        {
          id: 5,
          name: 'Assignment 5',
          revenue: 3840.0,
          hoursWorked: 96,
          customer: {
            id: 1,
            name: 'Company A',
          },
          revenueMonth: [
            {
              name: 'MARCH',
              revenue: 3840.0,
              weeks: [
                {
                  weekNumber: 1,
                  revenue: 320.0,
                  days: [
                    {
                      day: '2025-03-01',
                      revenue: 320.0,
                    },
                  ],
                },
                {
                  weekNumber: 2,
                  revenue: 1920.0,
                  days: [
                    {
                      day: '2025-03-02',
                      revenue: 320.0,
                    },
                    {
                      day: '2025-03-03',
                      revenue: 320.0,
                    },
                    {
                      day: '2025-03-04',
                      revenue: 320.0,
                    },
                    {
                      day: '2025-03-05',
                      revenue: 320.0,
                    },
                    {
                      day: '2025-03-06',
                      revenue: 320.0,
                    },
                    {
                      day: '2025-03-07',
                      revenue: 320.0,
                    },
                  ],
                },
                {
                  weekNumber: 3,
                  revenue: 1600.0,
                  days: [
                    {
                      day: '2025-03-10',
                      revenue: 320.0,
                    },
                    {
                      day: '2025-03-11',
                      revenue: 320.0,
                    },
                    {
                      day: '2025-03-12',
                      revenue: 320.0,
                    },
                    {
                      day: '2025-03-13',
                      revenue: 320.0,
                    },
                    {
                      day: '2025-03-14',
                      revenue: 320.0,
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
