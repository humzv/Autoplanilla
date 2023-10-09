import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPlanilla } from 'app/shared/model/planilla.model';
import { getEntities } from './planilla.reducer';

export const Planilla = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const planillaList = useAppSelector(state => state.planilla.entities);
  const loading = useAppSelector(state => state.planilla.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="planilla-heading" data-cy="PlanillaHeading">
        <Translate contentKey="autoPlanillaApp.planilla.home.title">Planillas</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoPlanillaApp.planilla.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/planilla/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoPlanillaApp.planilla.home.createLabel">Create new Planilla</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {planillaList && planillaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoPlanillaApp.planilla.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.planilla.tipo">Tipo</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.planilla.consecutivoBAC">Consecutivo BAC</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.planilla.planBAC">Plan BAC</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.planilla.nombre">Nombre</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.planilla.notas">Notas</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.planilla.aprobador">Aprobador</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.planilla.notificantes">Notificantes</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.planilla.moneda">Moneda</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.planilla.vacasMulti">Vacas Multi</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.planilla.grupoAprobador">Grupo Aprobador</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.planilla.pagador">Pagador</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.planilla.grupoContabilidad">Grupo Contabilidad</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.planilla.estado">Estado</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.planilla.user">User</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.planilla.company">Company</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {planillaList.map((planilla, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/planilla/${planilla.id}`} color="link" size="sm">
                      {planilla.id}
                    </Button>
                  </td>
                  <td>{planilla.tipo}</td>
                  <td>{planilla.consecutivoBAC}</td>
                  <td>{planilla.planBAC}</td>
                  <td>{planilla.nombre}</td>
                  <td>{planilla.notas}</td>
                  <td>{planilla.aprobador}</td>
                  <td>{planilla.notificantes}</td>
                  <td>
                    <Translate contentKey={`autoPlanillaApp.Currency.${planilla.moneda}`} />
                  </td>
                  <td>{planilla.vacasMulti}</td>
                  <td>{planilla.grupoAprobador}</td>
                  <td>{planilla.pagador}</td>
                  <td>{planilla.grupoContabilidad}</td>
                  <td>
                    <Translate contentKey={`autoPlanillaApp.Estado.${planilla.estado}`} />
                  </td>
                  <td>
                    {planilla.users
                      ? planilla.users.map((val, j) => (
                          <span key={j}>
                            {val.id}
                            {j === planilla.users.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>{planilla.company ? <Link to={`/company/${planilla.company.id}`}>{planilla.company.nombre}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/planilla/${planilla.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/planilla/${planilla.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/planilla/${planilla.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="autoPlanillaApp.planilla.home.notFound">No Planillas found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Planilla;
