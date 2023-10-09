import { IEmployee } from 'app/shared/model/employee.model';
import { IPlanilla } from 'app/shared/model/planilla.model';
import { IUser } from 'app/shared/model/user.model';
import { TipoCedulaCompany } from 'app/shared/model/enumerations/tipo-cedula-company.model';
import { Estado } from 'app/shared/model/enumerations/estado.model';

export interface ICompany {
  id?: number;
  nombre?: string;
  tipoIdentificacion?: TipoCedulaCompany;
  identificacion?: string;
  correoElectronico?: string;
  nombreComercial?: string | null;
  telefono?: number | null;
  provincia?: string | null;
  canton?: string | null;
  distrito?: string | null;
  barrio?: string | null;
  sennas?: string | null;
  estado?: Estado;
  website?: string | null;
  employees?: IEmployee[] | null;
  planillas?: IPlanilla[] | null;
  users?: IUser[] | null;
}

export const defaultValue: Readonly<ICompany> = {};
