import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pago.reducer';

export const PagoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const pagoEntity = useAppSelector(state => state.pago.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pagoDetailsHeading">
          <Translate contentKey="autoPlanillaApp.pago.detail.title">Pago</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{pagoEntity.id}</dd>
          <dt>
            <span id="userID">
              <Translate contentKey="autoPlanillaApp.pago.userID">User ID</Translate>
            </span>
          </dt>
          <dd>{pagoEntity.userID}</dd>
          <dt>
            <span id="company">
              <Translate contentKey="autoPlanillaApp.pago.company">Company</Translate>
            </span>
          </dt>
          <dd>{pagoEntity.company}</dd>
          <dt>
            <span id="sequenceBank">
              <Translate contentKey="autoPlanillaApp.pago.sequenceBank">Sequence Bank</Translate>
            </span>
          </dt>
          <dd>{pagoEntity.sequenceBank}</dd>
          <dt>
            <span id="salarioBruto">
              <Translate contentKey="autoPlanillaApp.pago.salarioBruto">Salario Bruto</Translate>
            </span>
          </dt>
          <dd>{pagoEntity.salarioBruto}</dd>
          <dt>
            <span id="adicionales">
              <Translate contentKey="autoPlanillaApp.pago.adicionales">Adicionales</Translate>
            </span>
          </dt>
          <dd>{pagoEntity.adicionales}</dd>
          <dt>
            <span id="deducciones">
              <Translate contentKey="autoPlanillaApp.pago.deducciones">Deducciones</Translate>
            </span>
          </dt>
          <dd>{pagoEntity.deducciones}</dd>
          <dt>
            <span id="otrasdeducciones">
              <Translate contentKey="autoPlanillaApp.pago.otrasdeducciones">Otrasdeducciones</Translate>
            </span>
          </dt>
          <dd>{pagoEntity.otrasdeducciones}</dd>
          <dt>
            <span id="socialSecurity">
              <Translate contentKey="autoPlanillaApp.pago.socialSecurity">Social Security</Translate>
            </span>
          </dt>
          <dd>{pagoEntity.socialSecurity}</dd>
          <dt>
            <span id="renta">
              <Translate contentKey="autoPlanillaApp.pago.renta">Renta</Translate>
            </span>
          </dt>
          <dd>{pagoEntity.renta}</dd>
          <dt>
            <span id="pagoNeto">
              <Translate contentKey="autoPlanillaApp.pago.pagoNeto">Pago Neto</Translate>
            </span>
          </dt>
          <dd>{pagoEntity.pagoNeto}</dd>
          <dt>
            <span id="adicionalesnodeducibles">
              <Translate contentKey="autoPlanillaApp.pago.adicionalesnodeducibles">Adicionalesnodeducibles</Translate>
            </span>
          </dt>
          <dd>{pagoEntity.adicionalesnodeducibles}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="autoPlanillaApp.pago.type">Type</Translate>
            </span>
          </dt>
          <dd>{pagoEntity.type}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="autoPlanillaApp.pago.status">Status</Translate>
            </span>
          </dt>
          <dd>{pagoEntity.status}</dd>
          <dt>
            <span id="processID">
              <Translate contentKey="autoPlanillaApp.pago.processID">Process ID</Translate>
            </span>
          </dt>
          <dd>{pagoEntity.processID}</dd>
          <dt>
            <Translate contentKey="autoPlanillaApp.pago.planilla">Planilla</Translate>
          </dt>
          <dd>{pagoEntity.planilla ? pagoEntity.planilla.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/pago" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pago/${pagoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PagoDetail;
