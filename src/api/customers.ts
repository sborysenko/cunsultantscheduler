import { API_BASE_URL } from './const';
import { Customer } from './types';

const mockEmployees: Customer[] = [
  {
    id: 1,
    name: 'Storebrand',
  },
];

export const getCustomersMock = async (): Promise<Customer[]> => {
  return Promise.resolve(mockEmployees);
};

export const getCustomers = async (): Promise<Customer[]> => {
  return fetch(`${API_BASE_URL}/customers`)
    .then((response) => response.json())
    .catch((error) => console.error('Error fetching customers:', error));
};
