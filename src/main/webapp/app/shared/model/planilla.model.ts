import { IPago } from 'app/shared/model/pago.model';
import { IDetallePago } from 'app/shared/model/detalle-pago.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { IUser } from 'app/shared/model/user.model';
import { ICompany } from 'app/shared/model/company.model';
import { Currency } from 'app/shared/model/enumerations/currency.model';
import { Estado } from 'app/shared/model/enumerations/estado.model';

export interface IPlanilla {
  id?: number;
  tipo?: string | null;
  consecutivoBAC?: number | null;
  planBAC?: string | null;
  nombre?: string | null;
  notas?: string | null;
  aprobador?: string | null;
  notificantes?: string | null;
  moneda?: Currency | null;
  vacasMulti?: number | null;
  grupoAprobador?: string | null;
  pagador?: string | null;
  grupoContabilidad?: string | null;
  estado?: Estado;
  pagos?: IPago[] | null;
  detallePagos?: IDetallePago[] | null;
  employees?: IEmployee[] | null;
  users?: IUser[] | null;
  company?: ICompany | null;
}

export const defaultValue: Readonly<IPlanilla> = {};
