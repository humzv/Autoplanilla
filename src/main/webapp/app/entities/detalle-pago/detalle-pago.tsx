import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDetallePago } from 'app/shared/model/detalle-pago.model';
import { getEntities } from './detalle-pago.reducer';

export const DetallePago = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const detallePagoList = useAppSelector(state => state.detallePago.entities);
  const loading = useAppSelector(state => state.detallePago.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="detalle-pago-heading" data-cy="DetallePagoHeading">
        <Translate contentKey="autoPlanillaApp.detallePago.home.title">Detalle Pagos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoPlanillaApp.detallePago.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/detalle-pago/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoPlanillaApp.detallePago.home.createLabel">Create new Detalle Pago</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {detallePagoList && detallePagoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoPlanillaApp.detallePago.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.detallePago.userID">User ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.detallePago.company">Company</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.detallePago.sequenceBank">Sequence Bank</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.detallePago.salarioBruto">Salario Bruto</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.detallePago.adicionales">Adicionales</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.detallePago.deducciones">Deducciones</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.detallePago.otrasdeducciones">Otrasdeducciones</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.detallePago.socialSecurity">Social Security</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.detallePago.renta">Renta</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.detallePago.pagoNeto">Pago Neto</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.detallePago.adicionalesnodeducibles">Adicionalesnodeducibles</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.detallePago.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.detallePago.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.detallePago.processID">Process ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.detallePago.planilla">Planilla</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {detallePagoList.map((detallePago, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/detalle-pago/${detallePago.id}`} color="link" size="sm">
                      {detallePago.id}
                    </Button>
                  </td>
                  <td>{detallePago.userID}</td>
                  <td>{detallePago.company}</td>
                  <td>{detallePago.sequenceBank}</td>
                  <td>{detallePago.salarioBruto}</td>
                  <td>{detallePago.adicionales}</td>
                  <td>{detallePago.deducciones}</td>
                  <td>{detallePago.otrasdeducciones}</td>
                  <td>{detallePago.socialSecurity}</td>
                  <td>{detallePago.renta}</td>
                  <td>{detallePago.pagoNeto}</td>
                  <td>{detallePago.adicionalesnodeducibles}</td>
                  <td>{detallePago.type}</td>
                  <td>
                    <Translate contentKey={`autoPlanillaApp.EstadoPago.${detallePago.status}`} />
                  </td>
                  <td>{detallePago.processID}</td>
                  <td>{detallePago.planilla ? <Link to={`/planilla/${detallePago.planilla.id}`}>{detallePago.planilla.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/detalle-pago/${detallePago.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/detalle-pago/${detallePago.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/detalle-pago/${detallePago.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="autoPlanillaApp.detallePago.home.notFound">No Detalle Pagos found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default DetallePago;
