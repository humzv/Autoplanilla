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
import { TipoCedulaCompany } from 'app/shared/model/enumerations/tipo-cedula-company.model';
import { Estado } from 'app/shared/model/enumerations/estado.model';
import { getEntity, updateEntity, createEntity, reset } from './company.reducer';

export const CompanyUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const companyEntity = useAppSelector(state => state.company.entity);
  const loading = useAppSelector(state => state.company.loading);
  const updating = useAppSelector(state => state.company.updating);
  const updateSuccess = useAppSelector(state => state.company.updateSuccess);
  const tipoCedulaCompanyValues = Object.keys(TipoCedulaCompany);
  const estadoValues = Object.keys(Estado);

  const handleClose = () => {
    navigate('/company');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...companyEntity,
      ...values,
      users: mapIdList(values.users),
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
          tipoIdentificacion: 'JURIDICA',
          estado: 'ACTIVO',
          ...companyEntity,
          users: companyEntity?.users?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="autoPlanillaApp.company.home.createOrEditLabel" data-cy="CompanyCreateUpdateHeading">
            <Translate contentKey="autoPlanillaApp.company.home.createOrEditLabel">Create or edit a Company</Translate>
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
                  id="company-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('autoPlanillaApp.company.nombre')}
                id="company-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('autoPlanillaApp.company.tipoIdentificacion')}
                id="company-tipoIdentificacion"
                name="tipoIdentificacion"
                data-cy="tipoIdentificacion"
                type="select"
              >
                {tipoCedulaCompanyValues.map(tipoCedulaCompany => (
                  <option value={tipoCedulaCompany} key={tipoCedulaCompany}>
                    {translate('autoPlanillaApp.TipoCedulaCompany.' + tipoCedulaCompany)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('autoPlanillaApp.company.identificacion')}
                id="company-identificacion"
                name="identificacion"
                data-cy="identificacion"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('autoPlanillaApp.company.correoElectronico')}
                id="company-correoElectronico"
                name="correoElectronico"
                data-cy="correoElectronico"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('autoPlanillaApp.company.nombreComercial')}
                id="company-nombreComercial"
                name="nombreComercial"
                data-cy="nombreComercial"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.company.telefono')}
                id="company-telefono"
                name="telefono"
                data-cy="telefono"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.company.provincia')}
                id="company-provincia"
                name="provincia"
                data-cy="provincia"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.company.canton')}
                id="company-canton"
                name="canton"
                data-cy="canton"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.company.distrito')}
                id="company-distrito"
                name="distrito"
                data-cy="distrito"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.company.barrio')}
                id="company-barrio"
                name="barrio"
                data-cy="barrio"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.company.sennas')}
                id="company-sennas"
                name="sennas"
                data-cy="sennas"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.company.estado')}
                id="company-estado"
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
                label={translate('autoPlanillaApp.company.website')}
                id="company-website"
                name="website"
                data-cy="website"
                type="text"
              />
              <ValidatedField
                label={translate('autoPlanillaApp.company.user')}
                id="company-user"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/company" replace color="info">
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

export default CompanyUpdate;
