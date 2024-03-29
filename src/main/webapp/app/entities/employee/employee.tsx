import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities } from './employee.reducer';

export const Employee = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const employeeList = useAppSelector(state => state.employee.entities);
  const loading = useAppSelector(state => state.employee.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="employee-heading" data-cy="EmployeeHeading">
        <Translate contentKey="autoPlanillaApp.employee.home.title">Employees</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoPlanillaApp.employee.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/employee/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoPlanillaApp.employee.home.createLabel">Create new Employee</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {employeeList && employeeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.firstName">First Name</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.lastName">Last Name</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.lastName2">Last Name 2</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.phoneNumber">Phone Number</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.nationality">Nationality</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.identification">Identification</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.identificationType">Identification Type</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.insuredNumber">Insured Number</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.hireDate">Hire Date</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.birthDate">Birth Date</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.gender">Gender</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.civilStatus">Civil Status</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.countryOfBirth">Country Of Birth</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.salary">Salary</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.jornada">Jornada</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.language">Language</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.daysOfShift">Days Of Shift</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.hoursOfShift">Hours Of Shift</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.jobCode">Job Code</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.costCenter">Cost Center</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.user">User</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.company">Company</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.employee.planilla">Planilla</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {employeeList.map((employee, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/employee/${employee.id}`} color="link" size="sm">
                      {employee.id}
                    </Button>
                  </td>
                  <td>{employee.firstName}</td>
                  <td>{employee.lastName}</td>
                  <td>{employee.lastName2}</td>
                  <td>{employee.email}</td>
                  <td>{employee.phoneNumber}</td>
                  <td>{employee.nationality}</td>
                  <td>{employee.identification}</td>
                  <td>
                    <Translate contentKey={`autoPlanillaApp.IdentificationType.${employee.identificationType}`} />
                  </td>
                  <td>{employee.insuredNumber}</td>
                  <td>{employee.hireDate ? <TextFormat type="date" value={employee.hireDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{employee.birthDate ? <TextFormat type="date" value={employee.birthDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    <Translate contentKey={`autoPlanillaApp.Gender.${employee.gender}`} />
                  </td>
                  <td>{employee.civilStatus}</td>
                  <td>{employee.countryOfBirth}</td>
                  <td>{employee.salary}</td>
                  <td>
                    <Translate contentKey={`autoPlanillaApp.JornadaType.${employee.jornada}`} />
                  </td>
                  <td>
                    <Translate contentKey={`autoPlanillaApp.Language.${employee.language}`} />
                  </td>
                  <td>{employee.daysOfShift}</td>
                  <td>{employee.hoursOfShift}</td>
                  <td>{employee.jobCode}</td>
                  <td>{employee.costCenter}</td>
                  <td>{employee.user ? employee.user.id : ''}</td>
                  <td>{employee.company ? <Link to={`/company/${employee.company.id}`}>{employee.company.nombre}</Link> : ''}</td>
                  <td>{employee.planilla ? <Link to={`/planilla/${employee.planilla.id}`}>{employee.planilla.nombre}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/employee/${employee.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/employee/${employee.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/employee/${employee.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="autoPlanillaApp.employee.home.notFound">No Employees found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Employee;
