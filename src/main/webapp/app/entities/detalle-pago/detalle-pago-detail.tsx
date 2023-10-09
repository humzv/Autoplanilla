import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './detalle-pago.reducer';

export const DetallePagoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const detallePagoEntity = useAppSelector(state => state.detallePago.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="detallePagoDetailsHeading">
          <Translate contentKey="autoPlanillaApp.detallePago.detail.title">DetallePago</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{detallePagoEntity.id}</dd>
          <dt>
            <span id="userID">
              <Translate contentKey="autoPlanillaApp.detallePago.userID">User ID</Translate>
            </span>
          </dt>
          <dd>{detallePagoEntity.userID}</dd>
          <dt>
            <span id="company">
              <Translate contentKey="autoPlanillaApp.detallePago.company">Company</Translate>
            </span>
          </dt>
          <dd>{detallePagoEntity.company}</dd>
          <dt>
            <span id="sequenceBank">
              <Translate contentKey="autoPlanillaApp.detallePago.sequenceBank">Sequence Bank</Translate>
            </span>
          </dt>
          <dd>{detallePagoEntity.sequenceBank}</dd>
          <dt>
            <span id="salarioBruto">
              <Translate contentKey="autoPlanillaApp.detallePago.salarioBruto">Salario Bruto</Translate>
            </span>
          </dt>
          <dd>{detallePagoEntity.salarioBruto}</dd>
          <dt>
            <span id="adicionales">
              <Translate contentKey="autoPlanillaApp.detallePago.adicionales">Adicionales</Translate>
            </span>
          </dt>
          <dd>{detallePagoEntity.adicionales}</dd>
          <dt>
            <span id="deducciones">
              <Translate contentKey="autoPlanillaApp.detallePago.deducciones">Deducciones</Translate>
            </span>
          </dt>
          <dd>{detallePagoEntity.deducciones}</dd>
          <dt>
            <span id="otrasdeducciones">
              <Translate contentKey="autoPlanillaApp.detallePago.otrasdeducciones">Otrasdeducciones</Translate>
            </span>
          </dt>
          <dd>{detallePagoEntity.otrasdeducciones}</dd>
          <dt>
            <span id="socialSecurity">
              <Translate contentKey="autoPlanillaApp.detallePago.socialSecurity">Social Security</Translate>
            </span>
          </dt>
          <dd>{detallePagoEntity.socialSecurity}</dd>
          <dt>
            <span id="renta">
              <Translate contentKey="autoPlanillaApp.detallePago.renta">Renta</Translate>
            </span>
          </dt>
          <dd>{detallePagoEntity.renta}</dd>
          <dt>
            <span id="pagoNeto">
              <Translate contentKey="autoPlanillaApp.detallePago.pagoNeto">Pago Neto</Translate>
            </span>
          </dt>
          <dd>{detallePagoEntity.pagoNeto}</dd>
          <dt>
            <span id="adicionalesnodeducibles">
              <Translate contentKey="autoPlanillaApp.detallePago.adicionalesnodeducibles">Adicionalesnodeducibles</Translate>
            </span>
          </dt>
          <dd>{detallePagoEntity.adicionalesnodeducibles}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="autoPlanillaApp.detallePago.type">Type</Translate>
            </span>
          </dt>
          <dd>{detallePagoEntity.type}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="autoPlanillaApp.detallePago.status">Status</Translate>
            </span>
          </dt>
          <dd>{detallePagoEntity.status}</dd>
          <dt>
            <span id="processID">
              <Translate contentKey="autoPlanillaApp.detallePago.processID">Process ID</Translate>
            </span>
          </dt>
          <dd>{detallePagoEntity.processID}</dd>
          <dt>
            <Translate contentKey="autoPlanillaApp.detallePago.planilla">Planilla</Translate>
          </dt>
          <dd>{detallePagoEntity.planilla ? detallePagoEntity.planilla.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/detalle-pago" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/detalle-pago/${detallePagoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DetallePagoDetail;
