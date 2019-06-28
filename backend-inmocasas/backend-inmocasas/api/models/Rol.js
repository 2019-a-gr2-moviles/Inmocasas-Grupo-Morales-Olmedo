/**
 * Rol.js
 *
 * @description :: A model definition represents a database table/collection.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {
    TipoRol:{
      type:'string',
      required:true,
      isIn: ['administrador','usuario']
    },
    usuariosDeRol: {
      collection: 'rolUsuario',
      via: 'fkRol'
    }
  },

};

