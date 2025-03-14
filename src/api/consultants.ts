import { API_BASE_URL } from './const';
import { Consultant } from './types';

const mockEmployees: Consultant[] = [
  {
    id: 1,
    name: 'MK',
    ratePerHour: 70,
  },
];

export const getConsultantsMock = async (): Promise<Consultant[]> => {
  return Promise.resolve(mockEmployees);
};

export const getConsultants = async (): Promise<Consultant[]> => {
  return fetch(`${API_BASE_URL}/customers`)
    .then((response) => response.json())
    .catch((error) => {
      console.error('Error fetching employee data:', error);
    });
};
