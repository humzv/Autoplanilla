import company from 'app/entities/company/company.reducer';
import employee from 'app/entities/employee/employee.reducer';
import planilla from 'app/entities/planilla/planilla.reducer';
import pago from 'app/entities/pago/pago.reducer';
import detallePago from 'app/entities/detalle-pago/detalle-pago.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  company,
  employee,
  planilla,
  pago,
  detallePago,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
