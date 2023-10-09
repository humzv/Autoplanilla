import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICompany } from 'app/shared/model/company.model';
import { getEntities } from './company.reducer';

export const Company = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const companyList = useAppSelector(state => state.company.entities);
  const loading = useAppSelector(state => state.company.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="company-heading" data-cy="CompanyHeading">
        <Translate contentKey="autoPlanillaApp.company.home.title">Companies</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="autoPlanillaApp.company.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/company/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="autoPlanillaApp.company.home.createLabel">Create new Company</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {companyList && companyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="autoPlanillaApp.company.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.company.nombre">Nombre</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.company.tipoIdentificacion">Tipo Identificacion</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.company.identificacion">Identificacion</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.company.correoElectronico">Correo Electronico</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.company.nombreComercial">Nombre Comercial</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.company.telefono">Telefono</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.company.provincia">Provincia</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.company.canton">Canton</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.company.distrito">Distrito</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.company.barrio">Barrio</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.company.sennas">Sennas</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.company.estado">Estado</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.company.website">Website</Translate>
                </th>
                <th>
                  <Translate contentKey="autoPlanillaApp.company.user">User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {companyList.map((company, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/company/${company.id}`} color="link" size="sm">
                      {company.id}
                    </Button>
                  </td>
                  <td>{company.nombre}</td>
                  <td>
                    <Translate contentKey={`autoPlanillaApp.TipoCedulaCompany.${company.tipoIdentificacion}`} />
                  </td>
                  <td>{company.identificacion}</td>
                  <td>{company.correoElectronico}</td>
                  <td>{company.nombreComercial}</td>
                  <td>{company.telefono}</td>
                  <td>{company.provincia}</td>
                  <td>{company.canton}</td>
                  <td>{company.distrito}</td>
                  <td>{company.barrio}</td>
                  <td>{company.sennas}</td>
                  <td>
                    <Translate contentKey={`autoPlanillaApp.Estado.${company.estado}`} />
                  </td>
                  <td>{company.website}</td>
                  <td>
                    {company.users
                      ? company.users.map((val, j) => (
                          <span key={j}>
                            {val.id}
                            {j === company.users.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/company/${company.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/company/${company.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/company/${company.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="autoPlanillaApp.company.home.notFound">No Companies found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Company;
