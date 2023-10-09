import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Planilla from './planilla';
import PlanillaDetail from './planilla-detail';
import PlanillaUpdate from './planilla-update';
import PlanillaDeleteDialog from './planilla-delete-dialog';

const PlanillaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Planilla />} />
    <Route path="new" element={<PlanillaUpdate />} />
    <Route path=":id">
      <Route index element={<PlanillaDetail />} />
      <Route path="edit" element={<PlanillaUpdate />} />
      <Route path="delete" element={<PlanillaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PlanillaRoutes;
