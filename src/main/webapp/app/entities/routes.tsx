import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Company from './company';
import Employee from './employee';
import Planilla from './planilla';
import Pago from './pago';
import DetallePago from './detalle-pago';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="company/*" element={<Company />} />
        <Route path="employee/*" element={<Employee />} />
        <Route path="planilla/*" element={<Planilla />} />
        <Route path="pago/*" element={<Pago />} />
        <Route path="detalle-pago/*" element={<DetallePago />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
