import axios from 'axios';

export interface Employee {
  id: number;
  name: string;
  position: string;
  department: string;
}

const mockEmployees: Employee[] = [
  {
    id: 1,
    name: 'MK',
    position: 'tech lead',
    department: 'TC',
  },
];

export const getEmployeesMock = async () => {
  return Promise.resolve(mockEmployees);
};

export const getEmployees = async () => {
  return axios
    .get('https://api.example.com/employees')
    .then((response) => {
      return response.data;
    })
    .catch((error) => {
      console.error('Error fetching employee data:', error);
    });
};
