import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './employee.reducer';

export const EmployeeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const employeeEntity = useAppSelector(state => state.employee.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="employeeDetailsHeading">
          <Translate contentKey="autoPlanillaApp.employee.detail.title">Employee</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.id}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="autoPlanillaApp.employee.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="autoPlanillaApp.employee.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.lastName}</dd>
          <dt>
            <span id="lastName2">
              <Translate contentKey="autoPlanillaApp.employee.lastName2">Last Name 2</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.lastName2}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="autoPlanillaApp.employee.email">Email</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.email}</dd>
          <dt>
            <span id="phoneNumber">
              <Translate contentKey="autoPlanillaApp.employee.phoneNumber">Phone Number</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.phoneNumber}</dd>
          <dt>
            <span id="nationality">
              <Translate contentKey="autoPlanillaApp.employee.nationality">Nationality</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.nationality}</dd>
          <dt>
            <span id="identification">
              <Translate contentKey="autoPlanillaApp.employee.identification">Identification</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.identification}</dd>
          <dt>
            <span id="identificationType">
              <Translate contentKey="autoPlanillaApp.employee.identificationType">Identification Type</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.identificationType}</dd>
          <dt>
            <span id="insuredNumber">
              <Translate contentKey="autoPlanillaApp.employee.insuredNumber">Insured Number</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.insuredNumber}</dd>
          <dt>
            <span id="hireDate">
              <Translate contentKey="autoPlanillaApp.employee.hireDate">Hire Date</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.hireDate ? <TextFormat value={employeeEntity.hireDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="birthDate">
              <Translate contentKey="autoPlanillaApp.employee.birthDate">Birth Date</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.birthDate ? <TextFormat value={employeeEntity.birthDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="autoPlanillaApp.employee.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.gender}</dd>
          <dt>
            <span id="civilStatus">
              <Translate contentKey="autoPlanillaApp.employee.civilStatus">Civil Status</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.civilStatus}</dd>
          <dt>
            <span id="countryOfBirth">
              <Translate contentKey="autoPlanillaApp.employee.countryOfBirth">Country Of Birth</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.countryOfBirth}</dd>
          <dt>
            <span id="salary">
              <Translate contentKey="autoPlanillaApp.employee.salary">Salary</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.salary}</dd>
          <dt>
            <span id="jornada">
              <Translate contentKey="autoPlanillaApp.employee.jornada">Jornada</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.jornada}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="autoPlanillaApp.employee.language">Language</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.language}</dd>
          <dt>
            <span id="daysOfShift">
              <Translate contentKey="autoPlanillaApp.employee.daysOfShift">Days Of Shift</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.daysOfShift}</dd>
          <dt>
            <span id="hoursOfShift">
              <Translate contentKey="autoPlanillaApp.employee.hoursOfShift">Hours Of Shift</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.hoursOfShift}</dd>
          <dt>
            <span id="jobCode">
              <Translate contentKey="autoPlanillaApp.employee.jobCode">Job Code</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.jobCode}</dd>
          <dt>
            <span id="costCenter">
              <Translate contentKey="autoPlanillaApp.employee.costCenter">Cost Center</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.costCenter}</dd>
          <dt>
            <Translate contentKey="autoPlanillaApp.employee.user">User</Translate>
          </dt>
          <dd>{employeeEntity.user ? employeeEntity.user.id : ''}</dd>
          <dt>
            <Translate contentKey="autoPlanillaApp.employee.company">Company</Translate>
          </dt>
          <dd>{employeeEntity.company ? employeeEntity.company.nombre : ''}</dd>
          <dt>
            <Translate contentKey="autoPlanillaApp.employee.planilla">Planilla</Translate>
          </dt>
          <dd>{employeeEntity.planilla ? employeeEntity.planilla.nombre : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee/${employeeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmployeeDetail;
