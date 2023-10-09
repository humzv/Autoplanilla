import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPago } from 'app/shared/model/pago.model';
import { getEntities } from './pago.reducer';

export const Pago = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const pagoList = useAppSelector(state => state.pago.entities);
  const loading = useAppSelector(state => state.pago.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="pago-heading" data-cy="PagoHeading">
        <Translate contentKey="autoPlanillaApp.pago.home.title">Pagos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoPlanillaApp.pago.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/pago/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoPlanillaApp.pago.home.createLabel">Create new Pago</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {pagoList && pagoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoPlanillaApp.pago.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.pago.userID">User ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.pago.company">Company</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.pago.sequenceBank">Sequence Bank</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.pago.salarioBruto">Salario Bruto</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.pago.adicionales">Adicionales</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.pago.deducciones">Deducciones</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.pago.otrasdeducciones">Otrasdeducciones</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.pago.socialSecurity">Social Security</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.pago.renta">Renta</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.pago.pagoNeto">Pago Neto</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.pago.adicionalesnodeducibles">Adicionalesnodeducibles</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.pago.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.pago.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.pago.processID">Process ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.pago.planilla">Planilla</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {pagoList.map((pago, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/pago/${pago.id}`} color="link" size="sm">
                      {pago.id}
                    </Button>
                  </td>
                  <td>{pago.userID}</td>
                  <td>{pago.company}</td>
                  <td>{pago.sequenceBank}</td>
                  <td>{pago.salarioBruto}</td>
                  <td>{pago.adicionales}</td>
                  <td>{pago.deducciones}</td>
                  <td>{pago.otrasdeducciones}</td>
                  <td>{pago.socialSecurity}</td>
                  <td>{pago.renta}</td>
                  <td>{pago.pagoNeto}</td>
                  <td>{pago.adicionalesnodeducibles}</td>
                  <td>{pago.type}</td>
                  <td>
                    <Translate contentKey={`autoPlanillaApp.EstadoPago.${pago.status}`} />
                  </td>
                  <td>{pago.processID}</td>
                  <td>{pago.planilla ? <Link to={`/planilla/${pago.planilla.id}`}>{pago.planilla.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/pago/${pago.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/pago/${pago.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/pago/${pago.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="autoPlanillaApp.pago.home.notFound">No Pagos found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Pago;
