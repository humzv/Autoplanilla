import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './planilla.reducer';

export const PlanillaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const planillaEntity = useAppSelector(state => state.planilla.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="planillaDetailsHeading">
          <Translate contentKey="autoPlanillaApp.planilla.detail.title">Planilla</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{planillaEntity.id}</dd>
          <dt>
            <span id="tipo">
              <Translate contentKey="autoPlanillaApp.planilla.tipo">Tipo</Translate>
            </span>
          </dt>
          <dd>{planillaEntity.tipo}</dd>
          <dt>
            <span id="consecutivoBAC">
              <Translate contentKey="autoPlanillaApp.planilla.consecutivoBAC">Consecutivo BAC</Translate>
            </span>
          </dt>
          <dd>{planillaEntity.consecutivoBAC}</dd>
          <dt>
            <span id="planBAC">
              <Translate contentKey="autoPlanillaApp.planilla.planBAC">Plan BAC</Translate>
            </span>
          </dt>
          <dd>{planillaEntity.planBAC}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="autoPlanillaApp.planilla.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{planillaEntity.nombre}</dd>
          <dt>
            <span id="notas">
              <Translate contentKey="autoPlanillaApp.planilla.notas">Notas</Translate>
            </span>
          </dt>
          <dd>{planillaEntity.notas}</dd>
          <dt>
            <span id="aprobador">
              <Translate contentKey="autoPlanillaApp.planilla.aprobador">Aprobador</Translate>
            </span>
          </dt>
          <dd>{planillaEntity.aprobador}</dd>
          <dt>
            <span id="notificantes">
              <Translate contentKey="autoPlanillaApp.planilla.notificantes">Notificantes</Translate>
            </span>
          </dt>
          <dd>{planillaEntity.notificantes}</dd>
          <dt>
            <span id="moneda">
              <Translate contentKey="autoPlanillaApp.planilla.moneda">Moneda</Translate>
            </span>
          </dt>
          <dd>{planillaEntity.moneda}</dd>
          <dt>
            <span id="vacasMulti">
              <Translate contentKey="autoPlanillaApp.planilla.vacasMulti">Vacas Multi</Translate>
            </span>
          </dt>
          <dd>{planillaEntity.vacasMulti}</dd>
          <dt>
            <span id="grupoAprobador">
              <Translate contentKey="autoPlanillaApp.planilla.grupoAprobador">Grupo Aprobador</Translate>
            </span>
          </dt>
          <dd>{planillaEntity.grupoAprobador}</dd>
          <dt>
            <span id="pagador">
              <Translate contentKey="autoPlanillaApp.planilla.pagador">Pagador</Translate>
            </span>
          </dt>
          <dd>{planillaEntity.pagador}</dd>
          <dt>
            <span id="grupoContabilidad">
              <Translate contentKey="autoPlanillaApp.planilla.grupoContabilidad">Grupo Contabilidad</Translate>
            </span>
          </dt>
          <dd>{planillaEntity.grupoContabilidad}</dd>
          <dt>
            <span id="estado">
              <Translate contentKey="autoPlanillaApp.planilla.estado">Estado</Translate>
            </span>
          </dt>
          <dd>{planillaEntity.estado}</dd>
          <dt>
            <Translate contentKey="autoPlanillaApp.planilla.user">User</Translate>
          </dt>
          <dd>
            {planillaEntity.users
              ? planillaEntity.users.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {planillaEntity.users && i === planillaEntity.users.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="autoPlanillaApp.planilla.company">Company</Translate>
          </dt>
          <dd>{planillaEntity.company ? planillaEntity.company.nombre : ''}</dd>
        </dl>
        <Button tag={Link} to="/planilla" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/planilla/${planillaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlanillaDetail;
