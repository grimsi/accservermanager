import { Component, OnInit } from '@angular/core';
import { InfoService } from '../../services/info.service';
import { InfoDto } from '../../models/dtos/InfoDto';

@Component({
  selector: 'app-system-info',
  templateUrl: './system-info.component.html',
  styleUrls: ['./system-info.component.scss']
})
export class SystemInfoComponent implements OnInit {

  loading = true;
  info: InfoDto;

  constructor(private sysInfoService: InfoService) {
  }

  ngOnInit() {
    this.sysInfoService.getInfo().subscribe((info: InfoDto) => {
      this.info = info;
      this.loading = false;
    });
  }

}
