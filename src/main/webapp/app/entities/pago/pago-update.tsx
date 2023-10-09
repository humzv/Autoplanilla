import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPlanilla } from 'app/shared/model/planilla.model';
import { getEntities as getPlanillas } from 'app/entities/planilla/planilla.reducer';
import { IPago } from 'app/shared/model/pago.model';
import { EstadoPago } from 'app/shared/model/enumerations/estado-pago.model';
import { getEntity, updateEntity, createEntity, reset } from './pago.reducer';

export const PagoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const planillas = useAppSelector(state => state.planilla.entities);
  const pagoEntity = useAppSelector(state => state.pago.entity);
  const loading = useAppSelector(state => state.pago.loading);
  const updating = useAppSelector(state => state.pago.updating);
  const updateSuccess = useAppSelector(state => state.pago.updateSuccess);
  const estadoPagoValues = Object.keys(EstadoPago);

  const handleClose = () => {
    navigate('/pago');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPlanillas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...pagoEntity,
      ...values,
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
      ? {}
      : {
          status: 'DRAFT',
          ...pagoEntity,
          planilla: pagoEntity?.planilla?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="autoPlanillaApp.pago.home.createOrEditLabel" data-cy="PagoCreateUpdateHeading">
            <Translate contentKey="autoPlanillaApp.pago.home.createOrEditLabel">Create or edit a Pago</Translate>
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
                  id="pago-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('autoPlanillaApp.pago.userID')}
                id="pago-userID"
                name="userID"
                data-cy="userID"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('autoPlanillaApp.pago.company')}
                id="pago-company"
                name="company"
                data-cy="company"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('autoPlanillaApp.pago.sequenceBank')}
                id="pago-sequenceBank"
                name="sequenceBank"
                data-cy="sequenceBank"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.pago.salarioBruto')}
                id="pago-salarioBruto"
                name="salarioBruto"
                data-cy="salarioBruto"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('autoPlanillaApp.pago.adicionales')}
                id="pago-adicionales"
                name="adicionales"
                data-cy="adicionales"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('autoPlanillaApp.pago.deducciones')}
                id="pago-deducciones"
                name="deducciones"
                data-cy="deducciones"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('autoPlanillaApp.pago.otrasdeducciones')}
                id="pago-otrasdeducciones"
                name="otrasdeducciones"
                data-cy="otrasdeducciones"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('autoPlanillaApp.pago.socialSecurity')}
                id="pago-socialSecurity"
                name="socialSecurity"
                data-cy="socialSecurity"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('autoPlanillaApp.pago.renta')}
                id="pago-renta"
                name="renta"
                data-cy="renta"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('autoPlanillaApp.pago.pagoNeto')}
                id="pago-pagoNeto"
                name="pagoNeto"
                data-cy="pagoNeto"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('autoPlanillaApp.pago.adicionalesnodeducibles')}
                id="pago-adicionalesnodeducibles"
                name="adicionalesnodeducibles"
                data-cy="adicionalesnodeducibles"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('autoPlanillaApp.pago.type')}
                id="pago-type"
                name="type"
                data-cy="type"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('autoPlanillaApp.pago.status')}
                id="pago-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {estadoPagoValues.map(estadoPago => (
                  <option value={estadoPago} key={estadoPago}>
                    {translate('autoPlanillaApp.EstadoPago.' + estadoPago)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('autoPlanillaApp.pago.processID')}
                id="pago-processID"
                name="processID"
                data-cy="processID"
                type="text"
              />
              <ValidatedField
                id="pago-planilla"
                name="planilla"
                data-cy="planilla"
                label={translate('autoPlanillaApp.pago.planilla')}
                type="select"
              >
                <option value="" key="0" />
                {planillas
                  ? planillas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/pago" replace color="info">
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

export default PagoUpdate;
