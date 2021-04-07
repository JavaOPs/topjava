$(function () {
    makeEditable({
            ajaxUrl: "user/meals/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "desc"
                    ]
                ]
            }),
        update: filter
        }
    );
});

function filter() {
    $.ajax({
        type: "GET",
        url: "user/meals/filter",
        data: $('#filteredForm').serialize()
    }).done(updateTableWithData)
}