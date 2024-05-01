import React from 'react';
import { render, screen } from '@testing-library/react';
import DashboardLayout from '../subComponents/DashboardLayout';
import { BrowserRouter } from 'react-router-dom';

describe('Dashboard Layout Component', () => {
    test('Renders component', () => {
        render(
            <BrowserRouter>
                <DashboardLayout />
            </BrowserRouter>
        );
        const element = screen.getByText('Wallet Balance');
        expect(element).toBeInTheDocument();
    });

});