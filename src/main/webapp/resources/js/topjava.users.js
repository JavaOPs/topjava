const userAjaxUrl = "ajax/admin/users/";

function enable(chkbox, id) {
    const enabled = chkbox.is(":checked");
//  https://stackoverflow.com/a/22213543/548473
    $.ajax({
        url: userAjaxUrl + id,
        type: "POST",
        data: "enabled=" + enabled
    }).done(function () {
        chkbox.closest("tr").attr("data-userEnabled", enabled);
        successNoty(enabled ? "common.enabled" : "common.disabled");
    }).fail(function () {
        $(chkbox).prop("checked", !enabled);
    });
}

// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: userAjaxUrl,
            datatableOpts: {
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email",
                        "render": function (data, type, row) {
                            if (type === "display") {
                                return "<a href='mailto:" + data + "'>" + data + "</a>";
                            }
                            return data;
                        }
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled",
                        "render": function (data, type, row) {
                            if (type === "display") {
                                return "<input type='checkbox' " + (data ? "checked" : "") + " onclick='enable($(this)," + row.id + ");'/>";
                            }
                            return data;
                        }
                    },
                    {
                        "data": "registered",
                        "render": function (date, type, row) {
                            if (type === "display") {
                                return date.substring(0, 10);
                            }
                            return date;
                        }
                    },
                    {
                        "orderable": false,
                        "defaultContent": "",
                        "render": renderEditBtn
                    },
                    {
                        "orderable": false,
                        "defaultContent": "",
                        "render": renderDeleteBtn
                    }
                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ],
                "createdRow": function (row, data, dataIndex) {
                    if (!data.enabled) {
                        $(row).attr("data-userEnabled", false);
                    }
                }
            },
            updateTable: function () {
                $.get(userAjaxUrl, updateTableByData);
            }
        }
    );
});