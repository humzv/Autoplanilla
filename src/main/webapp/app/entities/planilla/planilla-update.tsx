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
import { Currency } from 'app/shared/model/enumerations/currency.model';
import { Estado } from 'app/shared/model/enumerations/estado.model';
import { getEntity, updateEntity, createEntity, reset } from './planilla.reducer';

export const PlanillaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const companies = useAppSelector(state => state.company.entities);
  const planillaEntity = useAppSelector(state => state.planilla.entity);
  const loading = useAppSelector(state => state.planilla.loading);
  const updating = useAppSelector(state => state.planilla.updating);
  const updateSuccess = useAppSelector(state => state.planilla.updateSuccess);
  const currencyValues = Object.keys(Currency);
  const estadoValues = Object.keys(Estado);

  const handleClose = () => {
    navigate('/planilla');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getCompanies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...planillaEntity,
      ...values,
      users: mapIdList(values.users),
      company: companies.find(it => it.id.toString() === values.company.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          moneda: 'CRC',
          estado: 'ACTIVO',
          ...planillaEntity,
          users: planillaEntity?.users?.map(e => e.id.toString()),
          company: planillaEntity?.company?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="autoPlanillaApp.planilla.home.createOrEditLabel" data-cy="PlanillaCreateUpdateHeading">
            <Translate contentKey="autoPlanillaApp.planilla.home.createOrEditLabel">Create or edit a Planilla</Translate>
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
                  id="planilla-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('autoPlanillaApp.planilla.tipo')}
                id="planilla-tipo"
                name="tipo"
                data-cy="tipo"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.planilla.consecutivoBAC')}
                id="planilla-consecutivoBAC"
                name="consecutivoBAC"
                data-cy="consecutivoBAC"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.planilla.planBAC')}
                id="planilla-planBAC"
                name="planBAC"
                data-cy="planBAC"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.planilla.nombre')}
                id="planilla-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.planilla.notas')}
                id="planilla-notas"
                name="notas"
                data-cy="notas"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.planilla.aprobador')}
                id="planilla-aprobador"
                name="aprobador"
                data-cy="aprobador"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.planilla.notificantes')}
                id="planilla-notificantes"
                name="notificantes"
                data-cy="notificantes"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.planilla.moneda')}
                id="planilla-moneda"
                name="moneda"
                data-cy="moneda"
                type="select"
              >
                {currencyValues.map(currency => (
                  <option value={currency} key={currency}>
                    {translate('autoPlanillaApp.Currency.' + currency)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('autoPlanillaApp.planilla.vacasMulti')}
                id="planilla-vacasMulti"
                name="vacasMulti"
                data-cy="vacasMulti"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.planilla.grupoAprobador')}
                id="planilla-grupoAprobador"
                name="grupoAprobador"
                data-cy="grupoAprobador"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.planilla.pagador')}
                id="planilla-pagador"
                name="pagador"
                data-cy="pagador"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.planilla.grupoContabilidad')}
                id="planilla-grupoContabilidad"
                name="grupoContabilidad"
                data-cy="grupoContabilidad"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.planilla.estado')}
                id="planilla-estado"
                name="estado"
                data-cy="estado"
                type="select"
              >
                {estadoValues.map(estado => (
                  <option value={estado} key={estado}>
                    {translate('autoPlanillaApp.Estado.' + estado)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('autoPlanillaApp.planilla.user')}
                id="planilla-user"
                data-cy="user"
                type="select"
                multiple
                name="users"
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
                id="planilla-company"
                name="company"
                data-cy="company"
                label={translate('autoPlanillaApp.planilla.company')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/planilla" replace color="info">
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

export default PlanillaUpdate;
