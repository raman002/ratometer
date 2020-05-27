
$(document).ready(function () {
    let URL = window.location.href;

    if (URL.indexOf('teams') != -1)
        dashboardTables.loadTeamTable();
    else if (URL.indexOf('members') != -1)
        dashboardTables.loadMembersTable();

});

var dashboardTables = {

    loadTeamTable: function() {
        $('#teams').DataTable();
    },

    loadMembersTable: function() {
        $('#members').DataTable();
    }
}
