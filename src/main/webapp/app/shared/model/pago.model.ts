import { IPlanilla } from 'app/shared/model/planilla.model';
import { EstadoPago } from 'app/shared/model/enumerations/estado-pago.model';

export interface IPago {
  id?: number;
  userID?: string;
  company?: string;
  sequenceBank?: number | null;
  salarioBruto?: number;
  adicionales?: number;
  deducciones?: number;
  otrasdeducciones?: number;
  socialSecurity?: number;
  renta?: number;
  pagoNeto?: number;
  adicionalesnodeducibles?: number;
  type?: string;
  status?: EstadoPago;
  processID?: string | null;
  planilla?: IPlanilla | null;
}

export const defaultValue: Readonly<IPago> = {};
