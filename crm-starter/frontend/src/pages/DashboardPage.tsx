import { useEffect, useState } from 'react';
import api from '../api/client';

export default function DashboardPage() {
  const [stats, setStats] = useState({ totalCustomers: 0, openLeads: 0, totalRevenue: 0 });

  useEffect(() => {
    api.get('/dashboard')
      .then((response) => setStats(response.data))
      .catch((error) => console.error('Dashboard fetch failed', error));
  }, []);

  return (
    <div>
      <h2>CRM Dashboard</h2>
      <div className="cards">
        <div className="card">
          <h3>Total Customers</h3>
          <p>{stats.totalCustomers}</p>
        </div>
        <div className="card">
          <h3>Open Leads</h3>
          <p>{stats.openLeads}</p>
        </div>
        <div className="card">
          <h3>Total Revenue</h3>
          <p>${stats.totalRevenue}</p>
        </div>
      </div>
    </div>
  );
}
