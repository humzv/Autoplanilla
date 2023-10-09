import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DetallePago from './detalle-pago';
import DetallePagoDetail from './detalle-pago-detail';
import DetallePagoUpdate from './detalle-pago-update';
import DetallePagoDeleteDialog from './detalle-pago-delete-dialog';

const DetallePagoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DetallePago />} />
    <Route path="new" element={<DetallePagoUpdate />} />
    <Route path=":id">
      <Route index element={<DetallePagoDetail />} />
      <Route path="edit" element={<DetallePagoUpdate />} />
      <Route path="delete" element={<DetallePagoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DetallePagoRoutes;
