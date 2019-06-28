/**
 * Usuario.js
 *
 * @description :: A model definition represents a database table/collection.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {

    nombre:{
      type: 'string',
      required:true

    },
    apellido:{
      type: 'string',
      required:true

    },
    telefono:{
      type:'string',
      required:true,
      maxLength:10,
      minLength:10

    },
    nombreUsuario: {
      type: 'string',
      required:true

    },
    email:{
      type:'string',
      isEmail:true,
      required:true

    },
    contrasenia:{
      type:'string',
      required:true
    },
    rolesDeUsuario: {
      collection: 'rolUsuario',
      via: 'fkUsuario'
    },
    publicacionesDeUsuario: {
      collection: 'publicacion',
      via: 'fkUsuario'
    }


  },

};

