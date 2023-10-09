import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { ICompany } from 'app/shared/model/company.model';
import { getEntities as getCompanies } from 'app/entities/company/company.reducer';
import { IPlanilla } from 'app/shared/model/planilla.model';
import { getEntities as getPlanillas } from 'app/entities/planilla/planilla.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { IdentificationType } from 'app/shared/model/enumerations/identification-type.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { JornadaType } from 'app/shared/model/enumerations/jornada-type.model';
import { Language } from 'app/shared/model/enumerations/language.model';
import { getEntity, updateEntity, createEntity, reset } from './employee.reducer';

export const EmployeeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const companies = useAppSelector(state => state.company.entities);
  const planillas = useAppSelector(state => state.planilla.entities);
  const employeeEntity = useAppSelector(state => state.employee.entity);
  const loading = useAppSelector(state => state.employee.loading);
  const updating = useAppSelector(state => state.employee.updating);
  const updateSuccess = useAppSelector(state => state.employee.updateSuccess);
  const identificationTypeValues = Object.keys(IdentificationType);
  const genderValues = Object.keys(Gender);
  const jornadaTypeValues = Object.keys(JornadaType);
  const languageValues = Object.keys(Language);

  const handleClose = () => {
    navigate('/employee');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getCompanies({}));
    dispatch(getPlanillas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.hireDate = convertDateTimeToServer(values.hireDate);
    values.birthDate = convertDateTimeToServer(values.birthDate);

    const entity = {
      ...employeeEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
      company: companies.find(it => it.id.toString() === values.company.toString()),
      planilla: planillas.find(it => it.id.toString() === values.planilla.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          hireDate: displayDefaultDateTime(),
          birthDate: displayDefaultDateTime(),
        }
      : {
          identificationType: 'CN',
          gender: 'MALE',
          jornada: 'TiempoCompleto',
          language: 'FRENCH',
          ...employeeEntity,
          hireDate: convertDateTimeFromServer(employeeEntity.hireDate),
          birthDate: convertDateTimeFromServer(employeeEntity.birthDate),
          user: employeeEntity?.user?.id,
          company: employeeEntity?.company?.id,
          planilla: employeeEntity?.planilla?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="autoPlanillaApp.employee.home.createOrEditLabel" data-cy="EmployeeCreateUpdateHeading">
            <Translate contentKey="autoPlanillaApp.employee.home.createOrEditLabel">Create or edit a Employee</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="employee-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('autoPlanillaApp.employee.firstName')}
                id="employee-firstName"
                name="firstName"
                data-cy="firstName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('autoPlanillaApp.employee.lastName')}
                id="employee-lastName"
                name="lastName"
                data-cy="lastName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('autoPlanillaApp.employee.lastName2')}
                id="employee-lastName2"
                name="lastName2"
                data-cy="lastName2"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.employee.email')}
                id="employee-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('autoPlanillaApp.employee.phoneNumber')}
                id="employee-phoneNumber"
                name="phoneNumber"
                data-cy="phoneNumber"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.employee.nationality')}
                id="employee-nationality"
                name="nationality"
                data-cy="nationality"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('autoPlanillaApp.employee.identification')}
                id="employee-identification"
                name="identification"
                data-cy="identification"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('autoPlanillaApp.employee.identificationType')}
                id="employee-identificationType"
                name="identificationType"
                data-cy="identificationType"
                type="select"
              >
                {identificationTypeValues.map(identificationType => (
                  <option value={identificationType} key={identificationType}>
                    {translate('autoPlanillaApp.IdentificationType.' + identificationType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('autoPlanillaApp.employee.insuredNumber')}
                id="employee-insuredNumber"
                name="insuredNumber"
                data-cy="insuredNumber"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.employee.hireDate')}
                id="employee-hireDate"
                name="hireDate"
                data-cy="hireDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('autoPlanillaApp.employee.birthDate')}
                id="employee-birthDate"
                name="birthDate"
                data-cy="birthDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.employee.gender')}
                id="employee-gender"
                name="gender"
                data-cy="gender"
                type="select"
              >
                {genderValues.map(gender => (
                  <option value={gender} key={gender}>
                    {translate('autoPlanillaApp.Gender.' + gender)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('autoPlanillaApp.employee.civilStatus')}
                id="employee-civilStatus"
                name="civilStatus"
                data-cy="civilStatus"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.employee.countryOfBirth')}
                id="employee-countryOfBirth"
                name="countryOfBirth"
                data-cy="countryOfBirth"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.employee.salary')}
                id="employee-salary"
                name="salary"
                data-cy="salary"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('autoPlanillaApp.employee.jornada')}
                id="employee-jornada"
                name="jornada"
                data-cy="jornada"
                type="select"
              >
                {jornadaTypeValues.map(jornadaType => (
                  <option value={jornadaType} key={jornadaType}>
                    {translate('autoPlanillaApp.JornadaType.' + jornadaType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('autoPlanillaApp.employee.language')}
                id="employee-language"
                name="language"
                data-cy="language"
                type="select"
              >
                {languageValues.map(language => (
                  <option value={language} key={language}>
                    {translate('autoPlanillaApp.Language.' + language)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('autoPlanillaApp.employee.daysOfShift')}
                id="employee-daysOfShift"
                name="daysOfShift"
                data-cy="daysOfShift"
                type="text"
                validate={{
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('autoPlanillaApp.employee.hoursOfShift')}
                id="employee-hoursOfShift"
                name="hoursOfShift"
                data-cy="hoursOfShift"
                type="text"
                validate={{
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('autoPlanillaApp.employee.jobCode')}
                id="employee-jobCode"
                name="jobCode"
                data-cy="jobCode"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.employee.costCenter')}
                id="employee-costCenter"
                name="costCenter"
                data-cy="costCenter"
                type="text"
              />
              <ValidatedField
                id="employee-user"
                name="user"
                data-cy="user"
                label={translate('autoPlanillaApp.employee.user')}
                type="select"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="employee-company"
                name="company"
                data-cy="company"
                label={translate('autoPlanillaApp.employee.company')}
                type="select"
              >
                <option value="" key="0" />
                {companies
                  ? companies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombre}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="employee-planilla"
                name="planilla"
                data-cy="planilla"
                label={translate('autoPlanillaApp.employee.planilla')}
                type="select"
              >
                <option value="" key="0" />
                {planillas
                  ? planillas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nombre}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/employee" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default EmployeeUpdate;
