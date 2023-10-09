import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './company.reducer';

export const CompanyDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const companyEntity = useAppSelector(state => state.company.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="companyDetailsHeading">
          <Translate contentKey="autoPlanillaApp.company.detail.title">Company</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{companyEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="autoPlanillaApp.company.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{companyEntity.nombre}</dd>
          <dt>
            <span id="tipoIdentificacion">
              <Translate contentKey="autoPlanillaApp.company.tipoIdentificacion">Tipo Identificacion</Translate>
            </span>
          </dt>
          <dd>{companyEntity.tipoIdentificacion}</dd>
          <dt>
            <span id="identificacion">
              <Translate contentKey="autoPlanillaApp.company.identificacion">Identificacion</Translate>
            </span>
          </dt>
          <dd>{companyEntity.identificacion}</dd>
          <dt>
            <span id="correoElectronico">
              <Translate contentKey="autoPlanillaApp.company.correoElectronico">Correo Electronico</Translate>
            </span>
          </dt>
          <dd>{companyEntity.correoElectronico}</dd>
          <dt>
            <span id="nombreComercial">
              <Translate contentKey="autoPlanillaApp.company.nombreComercial">Nombre Comercial</Translate>
            </span>
          </dt>
          <dd>{companyEntity.nombreComercial}</dd>
          <dt>
            <span id="telefono">
              <Translate contentKey="autoPlanillaApp.company.telefono">Telefono</Translate>
            </span>
          </dt>
          <dd>{companyEntity.telefono}</dd>
          <dt>
            <span id="provincia">
              <Translate contentKey="autoPlanillaApp.company.provincia">Provincia</Translate>
            </span>
          </dt>
          <dd>{companyEntity.provincia}</dd>
          <dt>
            <span id="canton">
              <Translate contentKey="autoPlanillaApp.company.canton">Canton</Translate>
            </span>
          </dt>
          <dd>{companyEntity.canton}</dd>
          <dt>
            <span id="distrito">
              <Translate contentKey="autoPlanillaApp.company.distrito">Distrito</Translate>
            </span>
          </dt>
          <dd>{companyEntity.distrito}</dd>
          <dt>
            <span id="barrio">
              <Translate contentKey="autoPlanillaApp.company.barrio">Barrio</Translate>
            </span>
          </dt>
          <dd>{companyEntity.barrio}</dd>
          <dt>
            <span id="sennas">
              <Translate contentKey="autoPlanillaApp.company.sennas">Sennas</Translate>
            </span>
          </dt>
          <dd>{companyEntity.sennas}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="autoPlanillaApp.company.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{companyEntity.estado}</dd>
          <dt>
            <span id="website">
              <Translate contentKey="autoPlanillaApp.company.website">Website</Translate>
            </span>
          </dt>
          <dd>{companyEntity.website}</dd>
          <dt>
            <Translate contentKey="autoPlanillaApp.company.user">User</Translate>
          </dt>
          <dd>
            {companyEntity.users
              ? companyEntity.users.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {companyEntity.users && i === companyEntity.users.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/company" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/company/${companyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CompanyDetail;
