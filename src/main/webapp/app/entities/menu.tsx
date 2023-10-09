import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/company">
        <Translate contentKey="global.menu.entities.company" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/employee">
        <Translate contentKey="global.menu.entities.employee" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/planilla">
        <Translate contentKey="global.menu.entities.planilla" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/pago">
        <Translate contentKey="global.menu.entities.pago" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/detalle-pago">
        <Translate contentKey="global.menu.entities.detallePago" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
